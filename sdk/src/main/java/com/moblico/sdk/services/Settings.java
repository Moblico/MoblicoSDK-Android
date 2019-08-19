package com.moblico.sdk.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Settings {
    private static final String SHARED_PREFS_KEY = "MOBLICO_SETTINGS_JSON";
    private static final String OVERRIDDEN_SHARED_PREFS_KEY = "MOBLICO_SETTINGS_OVERRIDDEN";

    private final Map<String, String> mSettings = new HashMap<>();
    private final Map<String, String> mOverriddenSettings = new HashMap<>();
    private final SharedPreferences mSharedPrefs;

    Settings(final Context context) {
        mSharedPrefs = context.getSharedPreferences("MOBLICO_SETTINGS", Context.MODE_PRIVATE);
        if (mSharedPrefs.contains(SHARED_PREFS_KEY)) {
            parseJson(mSharedPrefs.getString(SHARED_PREFS_KEY, ""));
        }
        if (mSharedPrefs.contains(OVERRIDDEN_SHARED_PREFS_KEY)) {
            try {
                String storedHashMapString = mSharedPrefs.getString(OVERRIDDEN_SHARED_PREFS_KEY, "");
                java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
                }.getType();
                Gson gson = new Gson();
                HashMap<String, String> map = gson.fromJson(storedHashMapString, type);
                mOverriddenSettings.clear();
                mOverriddenSettings.putAll(map);
            } catch (Exception e) {
                // Ignore any errors here.
            }
        }
    }

    void parseNewSettings(String jsonSettings) {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putString(SHARED_PREFS_KEY, jsonSettings);
        editor.apply();
        parseJson(jsonSettings);
    }

    private void parseJson(String jsonSettings) {
        Type typeOfHashMap = new TypeToken<Map<String, String>>() {
        }.getType();
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(jsonSettings, typeOfHashMap);
        mSettings.clear();
        mSettings.putAll(map);
    }

    public void setOverrides(Map<String, String> overrides) {
        mOverriddenSettings.clear();
        mOverriddenSettings.putAll(overrides);
        Gson gson = new Gson();
        String hashMapString = gson.toJson(mOverriddenSettings);
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putString(OVERRIDDEN_SHARED_PREFS_KEY, hashMapString);
        editor.apply();
    }

    public String getString(final String key, final String defaultValue) {
        if (mOverriddenSettings.containsKey(key)) {
            return mOverriddenSettings.get(key);
        }
        if (mSettings.containsKey(key)) {
            return mSettings.get(key);
        }
        return defaultValue;
    }

    public int getInt(final String key, final int defaultValue) {
        String str = getString(key, null);
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (Throwable t) {
            return defaultValue;
        }
    }

    public long getLong(final String key, final long defaultValue) {
        String str = getString(key, null);
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (Throwable t) {
            return defaultValue;
        }
    }

    public float getFloat(final String key, final float defaultValue) {
        String str = getString(key, null);
        if (str == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(str);
        } catch (Throwable t) {
            return defaultValue;
        }
    }

    public boolean getBoolean(final String key, final boolean defaultValue) {
        String str = getString(key, null);
        if (str == null) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(str);
        } catch (Throwable t) {
            return defaultValue;
        }
    }

    public boolean hasKey(final String key) {
        return mSettings.containsKey(key) || mOverriddenSettings.containsKey(key);
    }
}
