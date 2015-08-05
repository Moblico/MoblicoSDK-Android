package com.moblico.sdk.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.moblico.sdk.entities.AuthenticationToken;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public final class Moblico {

    private static final String USERNAME_KEY = "MOBLICO_LOGIN_USERNAME";
    private static final String PASSWORD_KEY = "MOBLICO_LOGIN_PASSWORD";
    private static final String CLIENT_CODE_KEY = "MOBLICO_LOGIN_CLIENT_CODE";

    private static String sApiKey;
    private static AuthenticationToken sToken;
    private static Settings sSettings;
    private static boolean sLogging;
    private static boolean sTesting;
    private static String sUsername;
    private static String sPassword;
    private static String sClientCode;
    private static SharedPreferences sSharedPrefs;

    private Moblico() {
    }

    static URL buildUrl(final String path, Map<String, String> params) throws MalformedURLException {
        Uri.Builder b = new Uri.Builder();
        b.scheme("https");
        if(sTesting) {
            b.authority("moblicosandbox.com");
        } else {
            b.authority("moblico.net");
        }
        b.appendPath("services");
        b.appendPath("v4");
        b.appendEncodedPath(path);
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
        sSharedPrefs = context.getSharedPreferences("MOBLICO_LOGIN", Context.MODE_PRIVATE);
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

    public static void setTesting(boolean testing) {
        sTesting = testing;
    }

    public static void setUser(final String username, final String password, final boolean persist) {
        sUsername = username;
        sPassword = password;
        if (persist) {
            SharedPreferences.Editor edit = sSharedPrefs.edit();
            edit.putString(USERNAME_KEY, username);
            edit.putString(PASSWORD_KEY, password);
            edit.apply();
        } else {
            SharedPreferences.Editor edit = sSharedPrefs.edit();
            edit.clear();
            edit.apply();
        }
        sToken = null;
    }

    public static void setClientCode(final String clientCode) {
        sClientCode = clientCode;
        if(sSharedPrefs.contains(USERNAME_KEY)) {
            SharedPreferences.Editor edit = sSharedPrefs.edit();
            edit.putString(CLIENT_CODE_KEY, clientCode);
            edit.apply();
        }
        sToken = null;
    }

    public static String getUsername() {
        if(sUsername == null) {
            sUsername = sSharedPrefs.getString(USERNAME_KEY, null);
        }
        return sUsername;
    }

    public static String getPassword() {
        if(sPassword == null) {
            sPassword = sSharedPrefs.getString(PASSWORD_KEY, null);
        }
        return sPassword;
    }

    public static String getClientCode() {
        if(sClientCode == null) {
            sClientCode = sSharedPrefs.getString(CLIENT_CODE_KEY, null);
        }
        return sClientCode;
    }
}
