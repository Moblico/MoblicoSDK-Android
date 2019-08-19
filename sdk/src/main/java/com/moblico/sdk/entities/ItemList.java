package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ItemList implements Parcelable {
    private final long id;
    private final String name;
    private final Date lastUpdateDate;

    protected ItemList(Parcel in) {
        id = in.readLong();
        name = in.readString();
        long tmpLastUpdateDate = in.readLong();
        lastUpdateDate = tmpLastUpdateDate != -1 ? new Date(tmpLastUpdateDate) : null;
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
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeLong(lastUpdateDate != null ? lastUpdateDate.getTime() : -1L);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public String toString() {
        return "ItemList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
