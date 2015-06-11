package com.moblico.sdk.services;

import com.moblico.sdk.entities.AuthenticationToken;

public final class Moblico {

    private static String sApiKey;
    private static AuthenticationToken sToken;

    private Moblico() {
    }

    static void setToken(final AuthenticationToken token) {
        Moblico.sToken = token;
    }

    public static void setApiKey(final String apiKey) {
        sApiKey = apiKey;
    }

    public static AuthenticationService getAuthenticationService() {
        return new AuthenticationService();
    }

    public static AuthenticationToken getToken() {
        return sToken;
    }
}
