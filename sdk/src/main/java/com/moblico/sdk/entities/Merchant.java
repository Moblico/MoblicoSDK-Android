package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Merchant implements Parcelable {
    private final long id;
    private final String name;
    private final Map<String, String> attributes;

    protected Merchant(Parcel in) {
        id = in.readLong();
        name = in.readString();
        final int size = in.readInt();
        attributes = new HashMap<String, String>(size);
        for(int i = 0; i < size; i++){
            String key = in.readString();
            String value = in.readString();
            attributes.put(key,value);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeInt(attributes.size());
        for(Map.Entry<String,String> entry : attributes.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Merchant> CREATOR = new Creator<Merchant>() {
        @Override
        public Merchant createFromParcel(Parcel in) {
            return new Merchant(in);
        }

        @Override
        public Merchant[] newArray(int size) {
            return new Merchant[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean hasAttribute(final String attribute) {
        return attributes.containsKey(attribute);
    }

    public String getAttribute(final String attribute) {
        return attributes.get(attribute);
    }

    public Map<String,String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
