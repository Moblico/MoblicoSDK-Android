package com.moblico.sdk.services;

import android.content.Context;

import com.moblico.sdk.entities.User;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UsersService {

    private UsersService() {
    }

    public static void getUser(final String username, final Callback<User> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("users/" + username, null, new ErrorForwardingCallback<String>(callback) {

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
                // Use reflection to convert all the fields in User to <K:V> pairs.  This way, when
                // a field is added to user, we get it for free.  There might be easier ways to do
                // this with existing libraries...
                Map<String, String> params = new HashMap<String, String>();
                for(Field field : User.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        if (field.getType().equals(boolean.class)) {
                            params.put(field.getName(), field.getBoolean(user) ? "YES" : "NO");
                        } else if (field.getName().equals("attributes")) {
                            // Skip attributes!
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
                HttpRequest.post("users", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }

    public static String getUniqueUserId(final Context context) {
        return "Anonymous." + InstallationID.id(context);
    }
}
