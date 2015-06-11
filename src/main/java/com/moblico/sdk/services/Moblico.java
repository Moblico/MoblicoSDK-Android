package com.moblico.sdk.services;

import android.net.Uri;

import com.moblico.sdk.entities.AuthenticationToken;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public final class Moblico {

    private static String sApiKey;
    private static AuthenticationToken sToken;
    private static boolean sLogging;

    private Moblico() {
    }

    static URL buildUrl(final String path, Map<String, String> params) throws MalformedURLException {
        Uri.Builder b = new Uri.Builder();
        b.scheme("https");
        b.authority("moblico.net");
        b.appendPath("services");
        b.appendPath("v4");
        b.appendPath(path);
        if(params != null) {
            for(String key : params.keySet()) {
                b.appendQueryParameter(key, params.get(key));
            }
        }
        return new URL(b.build().toString());
    }

    static void setToken(final AuthenticationToken token) {
        Moblico.sToken = token;
    }

    static String getApiKey() {
        return sApiKey;
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

    public static boolean isLogging() {
        return sLogging;
    }

    public static void setLogging(final boolean logging) {
        sLogging = logging;
    }
}
