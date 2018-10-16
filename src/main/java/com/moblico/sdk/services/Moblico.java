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
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.moblico.sdk.entities.AuthenticationToken;
import com.moblico.sdk.entities.User;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public final class Moblico {

    public enum SocialType {
        NONE,
        FACEBOOK
    }

    private static final String USERNAME_KEY = "MOBLICO_LOGIN_USERNAME";
    private static final String PASSWORD_KEY = "MOBLICO_LOGIN_PASSWORD";
    private static final String CLIENT_CODE_KEY = "MOBLICO_LOGIN_CLIENT_CODE";
    private static final String USER_KEY = "MOBLICO_LOGIN_USER";
    private static final String TOKEN_KEY = "MOBLICO_LOGIN_TOKEN";

    private static String sApiKey;
    private static AuthenticationToken sToken;
    private static Observable sTokenObservers = new Observable();
    private static Settings sSettings;
    private static boolean sLogging;
    private static boolean sTesting;
    private static String sUsername;
    private static String sPassword;
    private static String sClientCode;
    private static SocialType sSocialType = SocialType.NONE;
    private static User sUser;
    private static SharedPreferences sSharedPrefs;
    private static Gson sGson;

    static {
        GsonBuilder builder = new GsonBuilder();
        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                } catch(Exception e) {
                    return null;
                }
            }
        });

        builder.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getTime());
            }
        });

        // Workaround for Doubles with "" string or "null" string values
        builder.registerTypeAdapter(Double.class, new JsonDeserializer<Double>() {
            public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                try {
                    return new Double(json.getAsJsonPrimitive().getAsDouble());
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

    public static void addTokenObserver(Observer observer) {
        sTokenObservers.addObserver(observer);
    }

    static void setToken(final AuthenticationToken token) {
        Moblico.sToken = token;
        sTokenObservers.notifyObservers(token);

        SharedPreferences.Editor edit = sSharedPrefs.edit();
        if (token != null) {
            String tokenStr = getGson().toJson(token);
            edit.putString(TOKEN_KEY, tokenStr);
        } else {
            edit.remove(TOKEN_KEY);
        }
        edit.apply();
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
        if (sToken == null && sSharedPrefs.contains(TOKEN_KEY)) {
            sToken = getGson().fromJson(sSharedPrefs.getString(TOKEN_KEY, ""), AuthenticationToken.class);
        }
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

    public static boolean isTesting() {
        return sTesting;
    }

    public static void setUser(final String username, final String password,
                               final SocialType socialType, final boolean persist) {
        if (socialType == null) {
            throw new IllegalArgumentException("socialType cannot be null");
        }
        if (socialType == SocialType.FACEBOOK && persist) {
            throw new UnsupportedOperationException("Cannot persist facebook logins!");
        }
        // Clear out the cached User object
        setUser(null);

        sUsername = username;
        sPassword = password;
        sSocialType = socialType;
        SharedPreferences.Editor edit = sSharedPrefs.edit();
        if (persist) {
            edit.putString(USERNAME_KEY, username);
            edit.putString(PASSWORD_KEY, password);
        } else {
            edit.clear();
        }
        edit.remove(TOKEN_KEY);
        edit.apply();
        sToken = null;
        sUser = null;
    }

    public static User getUser() {
        if (sUser == null && sSharedPrefs.contains(USER_KEY)) {
            sUser = getGson().fromJson(sSharedPrefs.getString(USER_KEY, ""), User.class);
        }
        return sUser;
    }

    protected static void setUser(User user) {
        sUser = user;
        SharedPreferences.Editor edit = sSharedPrefs.edit();
        if (user != null) {
            String userStr = getGson().toJson(user);
            edit.putString(USER_KEY, userStr);
        } else {
            edit.remove(USER_KEY);
        }
        edit.apply();
    }

    public static void clearUser() {
        setUser(null, null, SocialType.NONE, true);
        setClientCode(null);
        setToken(null);

        // Usually, after clearing the user, we call System.exit() to clear out any static variables
        // and the like.  On some newer versions of Android, this sometimes interrupts the asynchronous
        // storing of preferences.  So call commit() to block until we store updated preferences.
        SharedPreferences.Editor edit = sSharedPrefs.edit();
        edit.commit();
    }

    public static void setClientCode(final String clientCode) {
        sClientCode = clientCode;
        SharedPreferences.Editor edit = sSharedPrefs.edit();
        edit.putString(CLIENT_CODE_KEY, clientCode);
        edit.apply();
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
