package com.moblico.sdk.services;

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
}
