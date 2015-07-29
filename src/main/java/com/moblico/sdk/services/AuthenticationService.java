package com.moblico.sdk.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.moblico.sdk.entities.AuthenticationToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class AuthenticationService {

    AuthenticationService() {
    }

    /**
     * If needed, authenticate with the Moblico servers.  If we already have a valid authentication
     * token, {@link Callback.#onSuccess} will be called immediately.  Otherwise we make a server
     * request and eventually notify the callback.  The authentication token is cached internally,
     * so no extra information is sent to the callback, just a call to onSuccess or onFailure.
     */
    public void authenticate(final Callback<Void> callback) {
        if (Moblico.getToken() != null && Moblico.getToken().isValid()) {
            callback.onSuccess(null);
            return;
        }

        final Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", Moblico.getApiKey());
        params.put("platformName", "ANDROID");
        if (Moblico.getUsername() != null) {
            params.put("username", Moblico.getUsername());
            params.put("password", Moblico.getPassword());
            if (Moblico.getClientCode() != null) {
                params.put("childKeyword", Moblico.getClientCode());
            }
        }

        HttpRequest.get("authenticate", params, new Callback<String>() {
            @Override
            public void onSuccess(String result) {
                GsonBuilder builder = new GsonBuilder();
                // Register an adapter to manage the date types as long values
                final GsonBuilder gsonBuilder = builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                });
                final Gson gson = builder.create();
                final AuthenticationToken token = gson.fromJson(result, AuthenticationToken.class);
                Moblico.setToken(token);
                callback.onSuccess(null);
            }

            @Override
            public void onFailure(Throwable caught) {
                callback.onFailure(caught);
            }
        });
    }
}
