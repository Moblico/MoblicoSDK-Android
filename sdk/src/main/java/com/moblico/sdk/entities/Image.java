package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Image implements Parcelable {
    private final long id;
    private final String url;
    private final Date lastUpdateDate;

    protected Image(Parcel in) {
        id = in.readLong();
        url = in.readString();
        long tmpLastUpdateDate = in.readLong();
        lastUpdateDate = tmpLastUpdateDate != -1 ? new Date(tmpLastUpdateDate) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(url);
        dest.writeLong(lastUpdateDate != null ? lastUpdateDate.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
