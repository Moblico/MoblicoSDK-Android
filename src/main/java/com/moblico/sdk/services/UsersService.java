package com.moblico.sdk.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Media;
import com.moblico.sdk.entities.User;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
                        GsonBuilder builder = new GsonBuilder();
                        // Register an adapter to manage the date types as long values
                        // TODO: centralize this.
                        final GsonBuilder gsonBuilder = builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                return new Date(json.getAsJsonPrimitive().getAsLong());
                            }
                        });
                        final Gson gson = builder.create();
                        User user = gson.fromJson(result, User.class);
                        callback.onSuccess(user);
                    }
                });
            }
        });
    }
}
