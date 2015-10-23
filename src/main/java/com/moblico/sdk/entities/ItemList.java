package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemList implements Parcelable {
    private final long id;
    protected ItemList(Parcel in) {
        id = in.readLong();
    }

    public static final Creator<ItemList> CREATOR = new Creator<ItemList>() {
        @Override
        public ItemList createFromParcel(Parcel in) {
            return new ItemList(in);
        }

        @Override
        public ItemList[] newArray(int size) {
            return new ItemList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public long getId() {
        return id;
    }
}
