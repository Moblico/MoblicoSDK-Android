package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Group implements Parcelable {
    private final long id;
    private final String name;
    private final String description;
    private final boolean isRegisterable;
    private final Boolean belongs;

    protected Group(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        isRegisterable = in.readByte() != 0x00;
        byte belongsVal = in.readByte();
        belongs = belongsVal == 0x02 ? null : belongsVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeByte((byte) (isRegisterable ? 0x01 : 0x00));
        if (belongs == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (belongs ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Group> CREATOR = new Parcelable.Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRegisterable() {
        return isRegisterable;
    }

    public boolean doesBelong() {
        if (belongs == null) {
            return false;
        }
        return belongs;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isRegisterable=" + isRegisterable +
                ", belongs=" + belongs +
                '}';
    }
}
