package com.moblico.sdk.services;

import com.moblico.sdk.entities.AuthenticationToken;
import com.moblico.sdk.entities.Status;

import java.util.HashMap;
import java.util.Map;

public final class AuthenticationService {

    private static SocialTokenHandler sSocialTokenHandler;

    private AuthenticationService() {
    }

    public static void setSocialTokenHandler(SocialTokenHandler tokenHandler) {
        sSocialTokenHandler = tokenHandler;
    }

    /**
     * If needed, authenticate with the Moblico servers.  If we already have a valid authentication
     * token, {@link Callback#onSuccess} will be called immediately.  Otherwise we make a server
     * request and eventually notify the callback.  The authentication token is cached internally,
     * so no extra information is sent to the callback, just a call to onSuccess or onFailure.
     */
    public static void authenticate(final Callback<Void> callback) {
        if (Moblico.getToken() != null && Moblico.getToken().isValid()) {
            callback.onSuccess(null);
            return;
        }

        final Map<String, String> params = new HashMap<>();
        params.put("apikey", Moblico.getApiKey());
        params.put("platformName", "ANDROID");
        if (Moblico.getUsername() != null) {
            params.put("username", Moblico.getUsername());

            Moblico.SocialType socialType = Moblico.getSocialType();
            if (socialType == Moblico.SocialType.NONE) {
                params.put("password", Moblico.getPassword());
            } else {
                if (sSocialTokenHandler == null || sSocialTokenHandler.getToken(socialType) == null) {
                    throw new UnsupportedOperationException("Unhandled social type: " + socialType);
                }
                params.put("social", socialType.name());
                params.put("socialToken", sSocialTokenHandler.getToken(socialType));
            }
        }

        if (Moblico.getClientCode() != null) {
            params.put("childKeyword", Moblico.getClientCode());
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
                if (caught instanceof StatusCodeException) {
                    StatusCodeException ex = (StatusCodeException) caught;
                    if (ex.getStatus() != null &&
                            (ex.getStatus().getStatusType() == Status.StatusType.PASSWORD_MISMATCH ||
                                    ex.getStatus().getStatusType() == Status.StatusType.INVALID_USER ||
                                    ex.getStatus().getStatusType() == Status.StatusType.INVALID_PASSWORD)
                            && Moblico.getUsername() != null) {
                        // We had the wrong username/password.  Reset and try again.  This is needed
                        // because we persist the username/password, and we don't want to have all
                        // possible first startup queries to add this logic.
                        Moblico.clearUser();
                        authenticate(callback);
                        return;
                    }
                }
                callback.onFailure(caught);
            }
        });
    }

    public interface SocialTokenHandler {
        String getToken(Moblico.SocialType socialType);
    }
}
