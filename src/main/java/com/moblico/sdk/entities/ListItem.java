package com.moblico.sdk.entities;


import android.os.Parcel;
import android.os.Parcelable;

public class ListItem implements Parcelable {
    private final long id;
    private final long groupId;
    private final String name;
    private final String description;
    private final int count;
    private final boolean isChecked;
    private final boolean isFavorite;
    private final String status;

    public ListItem(long id, long groupId, String name, String description, int count, boolean isChecked, boolean isFavorite, String status) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.count = count;
        this.isChecked = isChecked;
        this.isFavorite = isFavorite;
        this.status = status;
    }

    protected ListItem(Parcel in) {
        id = in.readLong();
        groupId = in.readLong();
        name = in.readString();
        description = in.readString();
        count = in.readInt();
        isChecked = in.readByte() != 0x00;
        isFavorite = in.readByte() != 0x00;
        status = in.readString();
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
        dest.writeLong(groupId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(count);
        dest.writeByte((byte) (isChecked ? 0x01 : 0x00));
        dest.writeByte((byte) (isChecked ? 0x01 : 0x00));
        dest.writeString(status);
    }

    public long getId() {
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

    public long getGroupId() {
        return groupId;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", count=" + count +
                ", isChecked=" + isChecked +
                ", isFavorite=" + isFavorite +
                ", status='" + status + '\'' +
                '}';
    }
}
