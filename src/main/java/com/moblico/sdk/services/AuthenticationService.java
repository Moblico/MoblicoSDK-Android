package com.moblico.sdk.services;

import com.facebook.AccessToken;
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
            switch (Moblico.getSocialType()) {
                case FACEBOOK:
                    params.put("social", "FACEBOOK");
                    if (AccessToken.getCurrentAccessToken() != null) {
                        // The Facebook SDK says this should always exist and be up to date, if we've
                        // already logged the user in.  If this isn't the case, things get tricky.
                        // We need to show the user a new login activity/fragment.
                        params.put("socialToken", AccessToken.getCurrentAccessToken().getToken());
                    }
                    break;
                case NONE:
                    params.put("password", Moblico.getPassword());
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown social type: " + Moblico.getSocialType());
            }
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
