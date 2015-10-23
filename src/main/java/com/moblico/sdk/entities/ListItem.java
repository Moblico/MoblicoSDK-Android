package com.moblico.sdk.entities;


import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable {
    private final Long id;
    private final String name;
    private final String description;
    private final int count;
    private final boolean isChecked;
    private final boolean isFavorite;

    public ListItem(Long id, String name, String description, int count, boolean isChecked, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.count = count;
        this.isChecked = isChecked;
        this.isFavorite = isFavorite;
    }

    protected ListItem(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        count = in.readInt();
        isChecked = in.readByte() != 0x00;
        isFavorite = in.readByte() != 0x00;
    }

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
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
        dest.writeString(description);
        dest.writeInt(count);
        dest.writeByte((byte) (isChecked ? 0x01 : 0x00));
        dest.writeByte((byte) (isChecked ? 0x01 : 0x00));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCount() {
        return count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", count=" + count +
                ", isChecked=" + isChecked +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
