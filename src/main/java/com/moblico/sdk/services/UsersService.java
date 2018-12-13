package com.moblico.sdk.services;

import android.content.Context;
import android.net.Uri;

import com.google.gson.JsonObject;
import com.moblico.sdk.entities.Status;
import com.moblico.sdk.entities.User;

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
                        // Moblico usernames aren't case sensitive.
                        if (user.getUsername() != null && user.getUsername().equalsIgnoreCase(Moblico.getUsername())) {
                            Moblico.setUser(user);
                        }
                        callback.onSuccess(user);
                    }
                });
            }
        });
    }

    public static void lookupUser(final String email, final Callback<String> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("lookup", "true");
                HttpRequest.get("users/exists", params, new ErrorForwardingCallback<String>(callback) {

                    @Override
                    public void onSuccess(String result) {
                        JsonObject jobj = Moblico.getGson().fromJson(result, JsonObject.class);
                        final String username = jobj.get("username").getAsString();
                        callback.onSuccess(username);
                    }
                });
            }
        });
    }

    public static void registerUser(final User user, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = user.getParams();
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
                Map<String, String> params = new HashMap();
                Map<String, String> existingParams = new HashMap();
                if (Moblico.getUser() != null) {
                    // Load all existing user params here.  We will subtract these values from the
                    // updated values to only send the values that have changed.
                    existingParams.putAll(Moblico.getUser().getParams());
                }
                // Load the params for the updated user and compare them
                for (Map.Entry<String, String> entry : user.getParams().entrySet()) {
                    if (!existingParams.containsKey(entry.getKey()) ||
                            !existingParams.get(entry.getKey()).equals(entry.getValue())) {
                        // This param is either new or changed.  Send it to the server.
                        params.put(entry.getKey(), entry.getValue());
                    }
                }
                // It never makes sense to send the username parameter.  If the username has changed,
                // we use the 'newUsername' parameter.
                params.remove("username");
                if (!username.equals(user.getUsername())) {
                    params.put("newUsername", user.getUsername());
                }
                HttpRequest.put("users/" + Uri.encode(username), params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        // Call getUser() here so Moblico.getUser() has updated information.
                        getUser(user.getUsername(), new ErrorForwardingCallback<User>(callback) {
                            @Override
                            public void onSuccess(User result) {
                                callback.onSuccess(null);
                            }
                        });
                    }
                });
            }
        });
    }

    public static String getAnonymousUserId(final Context context) {
        return "Anonymous." + InstallationID.id(context);
    }
}
