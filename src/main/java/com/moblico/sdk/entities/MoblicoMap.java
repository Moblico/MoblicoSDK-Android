package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class MoblicoMap implements Parcelable {

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

    private MoblicoMap(Parcel in) {
        int size = in.readInt();
        for(int i = 0; i < size; i++){
            String key = in.readString();
            String value = in.readString();
            mMap.put(key,value);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMap.size());
        for(Map.Entry<String,String> entry : mMap.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MoblicoMap> CREATOR = new Parcelable.Creator<MoblicoMap>() {
        @Override
        public MoblicoMap createFromParcel(Parcel in) {
            return new MoblicoMap(in);
        }

        @Override
        public MoblicoMap[] newArray(int size) {
            return new MoblicoMap[size];
        }
    };
}
