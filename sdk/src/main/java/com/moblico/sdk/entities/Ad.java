package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Ad implements Parcelable {
    private final long id;
    private final Date createDate;
    private final Date lastUpdateDate;
    private final String name;
    private final String advertiserName;
    private final String clickToCall;
    private final String clickToUrl;
    private final Image image;

    protected Ad(Parcel in) {
        id = in.readLong();
        long tmpCreateDate = in.readLong();
        createDate = tmpCreateDate != -1 ? new Date(tmpCreateDate) : null;
        long tmpLastUpdateDate = in.readLong();
        lastUpdateDate = tmpLastUpdateDate != -1 ? new Date(tmpLastUpdateDate) : null;
        name = in.readString();
        advertiserName = in.readString();
        clickToCall = in.readString();
        clickToUrl = in.readString();
        image = (Image) in.readParcelable(Image.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(createDate != null ? createDate.getTime() : -1L);
        dest.writeLong(lastUpdateDate != null ? lastUpdateDate.getTime() : -1L);
        dest.writeString(name);
        dest.writeString(advertiserName);
        dest.writeString(clickToCall);
        dest.writeString(clickToUrl);
        dest.writeParcelable(image, 0);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ad> CREATOR = new Parcelable.Creator<Ad>() {
        @Override
        public Ad createFromParcel(Parcel in) {
            return new Ad(in);
        }

        @Override
        public Ad[] newArray(int size) {
            return new Ad[size];
        }
    };

    public long getId() {
        return id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public String getName() {
        return name;
    }

    public String getAdvertiserName() {
        return advertiserName;
    }

    public String getClickToCall() {
        return clickToCall;
    }

    public String getClickToUrl() {
        return clickToUrl;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ad ad = (Ad) o;

        return id == ad.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", name='" + name + '\'' +
                ", advertiserName='" + advertiserName + '\'' +
                ", clickToCall='" + clickToCall + '\'' +
                ", clickToUrl='" + clickToUrl + '\'' +
                ", image=" + image +
                '}';
    }
}
