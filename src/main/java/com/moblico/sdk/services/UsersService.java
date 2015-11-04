package com.moblico.sdk.services;

import android.content.Context;
import android.net.Uri;

import com.moblico.sdk.entities.Status;
import com.moblico.sdk.entities.User;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UsersService {

    private UsersService() {
    }

    public static void userExists(final String username, final Callback<Boolean> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                HttpRequest.get("users/exists", params, new ErrorForwardingCallback<String>(callback) {

                    @Override
                    public void onSuccess(String result) {
                        Status status = Moblico.getGson().fromJson(result, Status.class);
                        if (status.hasStatus() && status.getStatusType() == Status.StatusType.USER_NOT_FOUND) {
                            // This is a bit of a hack.  The return value for user exists vs doesn't
                            // exist is not consistent.
                            callback.onSuccess(false);
                        } else {
                            callback.onSuccess(true);
                        }
                    }
                });
            }
        });
    }

    public static void resetPassword(final String username, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.post("users/" + Uri.encode(username) + "/resetPassword", null, new ErrorForwardingCallback<String>(callback) {

                    @Override
                    public void onSuccess(String result) {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }

    public static void getUser(final String username, final Callback<User> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("users/" + Uri.encode(username), null, new ErrorForwardingCallback<String>(callback) {

                    @Override
                    public void onSuccess(String result) {
                        User user = Moblico.getGson().fromJson(result, User.class);
                        callback.onSuccess(user);
                    }
                });
            }
        });
    }

    public static void registerUser(final User user, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = getUserParams(user);
                HttpRequest.post("users", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }

    // We must pass in the username, in case that value is changing.
    public static void updateUser(final String username, final User user, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = getUserParams(user);
                // It never makes sense to send the username parameter.  If the username has changed,
                // we use the 'newUsername' parameter.
                params.remove("username");
                if (!username.equals(user.getUsername())) {
                    params.put("newUsername", user.getUsername());
                }
                HttpRequest.put("users/" + Uri.encode(username), params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }

    public static String getAnonymousUserId(final Context context) {
        return "Anonymous." + InstallationID.id(context);
    }

    private static Map<String, String> getUserParams(final User user) {
        // Use reflection to convert all the fields in User to <K:V> pairs.  This way, when
        // a field is added to user, we get it for free.  There might be easier ways to do
        // this with existing libraries...
        Map<String, String> params = new HashMap<String, String>();
        params.putAll(user.getAttributes());
        for(Field field : User.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.getType().equals(boolean.class)) {
                    params.put(field.getName(), field.getBoolean(user) ? "YES" : "NO");
                } else if (field.getName().equals("attributes")) {
                    // Skip attributes!  They were already added above.
                } else if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                    // Skip static fields!
                } else {
                    Object value = field.get(user);
                    if (value != null) {
                        params.put(field.getName(), value.toString());
                    }
                }
            } catch (IllegalAccessException e) {
            }
        }
        return params;
    }
}
