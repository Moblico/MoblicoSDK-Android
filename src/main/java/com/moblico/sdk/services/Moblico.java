package com.moblico.sdk.services;

import android.content.Context;
import android.location.Location;
import android.net.Uri;

import com.moblico.sdk.entities.AuthenticationToken;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public final class Moblico {

    private static String sApiKey;
    private static AuthenticationToken sToken;
    private static Settings sSettings;
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
        if(sToken != null && sToken.isValid()) {
            b.appendQueryParameter("token", sToken.getToken());
        }
        return new URL(b.build().toString());
    }

    static void setToken(final AuthenticationToken token) {
        // TODO: persist this?
        Moblico.sToken = token;
    }

    static String getApiKey() {
        return sApiKey;
    }

    public static void setApiKey(final String apiKey, final Context context) {
        sApiKey = apiKey;
        sSettings = new Settings(context);
    }

    public static AuthenticationService getAuthenticationService() {
        return new AuthenticationService();
    }

    public static LocationsService getLocationsService() {
        return new LocationsService();
    }

    public static SettingsService getSettingsService() {
        return new SettingsService();
    }

    public static Settings getSettings() {
        if(sSettings == null) {
            throw new IllegalStateException("Cannot get settings until setApiKey() has been called.");
        }
        return sSettings;
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
