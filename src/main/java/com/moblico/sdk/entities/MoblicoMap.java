package com.moblico.sdk.entities;

import java.util.HashMap;

public class MoblicoMap {

    private static final String MAP_PAIR_DELIMITER = ";";
    private static final String MAP_KEY_VALUE_DELIMITER = ",";

    private final HashMap<String, String> mMap = new HashMap<>();

    public MoblicoMap(final String mapString) {
        if (mapString != null) {
            for (String pair : mapString.split(MAP_PAIR_DELIMITER)) {
                String keyValue[] = pair.split(MAP_KEY_VALUE_DELIMITER, 2);
                if (keyValue.length < 2) {
                    continue;
                }
                mMap.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
    }

    public boolean containsKey(final String key) {
        return mMap.containsKey(key);
    }

    public String getString(final String key, final String defaultValue) {
        if (mMap.containsKey(key)) {
            return mMap.get(key);
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
}
