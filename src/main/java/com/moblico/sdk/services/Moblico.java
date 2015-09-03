package com.moblico.sdk.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.moblico.sdk.entities.AuthenticationToken;
import com.moblico.sdk.entities.User;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Map;

public final class Moblico {

    public enum SocialType {
        NONE,
        FACEBOOK
    }

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
    private static SocialType sSocialType = SocialType.NONE;
    private static SharedPreferences sSharedPrefs;
    private static Gson sGson;

    static {
        GsonBuilder builder = new GsonBuilder();
        // Register an adapter to manage the date types as long values
        final GsonBuilder gsonBuilder = builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                } catch(Exception e) {
                    return null;
                }
            }
        });
        sGson = builder.create();
    }

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

    public static Gson getGson() {
        return sGson;
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

    public static void setUser(final String username, final String password,
                               final SocialType socialType, final boolean persist) {
        if (socialType == SocialType.FACEBOOK && persist) {
            throw new UnsupportedOperationException("Cannot persist facebook logins!");
        }
        sUsername = username;
        sPassword = password;
        sSocialType = socialType;
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


    public static void clearUser() {
        setUser(null, null, SocialType.NONE, true);
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

    public static SocialType getSocialType() {
        return sSocialType;
    }
}
