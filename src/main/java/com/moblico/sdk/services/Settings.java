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

    private final Map<String, String> mSettings = new HashMap<String, String>();
    private final SharedPreferences mSharedPrefs;

    Settings(final Context context) {
        mSharedPrefs = context.getSharedPreferences("MOBLICO_SETTINGS", Context.MODE_PRIVATE);
        if (mSharedPrefs.contains(SHARED_PREFS_KEY)) {
            parseJson(mSharedPrefs.getString(SHARED_PREFS_KEY, ""));
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

    public String getString(final String key, final String defaultValue) {
        if (mSettings.containsKey(key)) {
            return mSettings.get(key);
        }
        return defaultValue;
    }

    public int getInt(final String key, final int defaultValue) {
        if (mSettings.containsKey(key)) {
            try {
                return Integer.parseInt(mSettings.get(key));
            } catch (Throwable t) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public boolean getBoolean(final String key, final boolean defaultValue) {
        if (mSettings.containsKey(key)) {
            try {
                return Boolean.parseBoolean(mSettings.get(key));
            } catch (Throwable t) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public boolean hasKey(final String key) {
        return mSettings.containsKey(key);
    }
}
