package com.moblico.sdk.services;

import com.moblico.sdk.entities.AuthenticationToken;

import java.util.HashMap;
import java.util.Map;

public final class AuthenticationService {

    private AuthenticationService() {
    }

    /**
     * If needed, authenticate with the Moblico servers.  If we already have a valid authentication
     * token, {@link Callback.#onSuccess} will be called immediately.  Otherwise we make a server
     * request and eventually notify the callback.  The authentication token is cached internally,
     * so no extra information is sent to the callback, just a call to onSuccess or onFailure.
     */
    public static void authenticate(final Callback<Void> callback) {
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
                final AuthenticationToken token = Moblico.getGson().fromJson(result, AuthenticationToken.class);
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
