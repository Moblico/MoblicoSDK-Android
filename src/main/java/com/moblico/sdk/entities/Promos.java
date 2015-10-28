package com.moblico.sdk.entities;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Promos implements Parcelable{
    private final long id;
    private final Date lastUpdateDate;
    private final String caption;
    private final Image image;
    private final String clickToCall;
    private final String clickToUrl;
    private final String priority;

    protected Promos(Parcel in) {
        id = in.readLong();
        long tmpLastUpdateDate = in.readLong();
        lastUpdateDate = tmpLastUpdateDate != -1 ? new Date(tmpLastUpdateDate) : null;
        caption = in.readString();
        image = (Image) in.readParcelable(Image.class.getClassLoader());
        clickToCall = in.readString();
        clickToUrl = in.readString();
        priority = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(lastUpdateDate != null ? lastUpdateDate.getTime() : -1L);
        dest.writeString(caption);
        dest.writeParcelable(image, 0);
        dest.writeString(clickToCall);
        dest.writeString(clickToUrl);
        dest.writeString(priority);
    }

    public static final Creator<Promos> CREATOR = new Creator<Promos>() {
        @Override
        public Promos createFromParcel(Parcel in) {
            return new Promos(in);
        }

        @Override
        public Promos[] newArray(int size) {
            return new Promos[size];
        }
    };

    public long getId() {
        return id;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public String getCaption() {
        return caption;
    }

    public Image getImage() {
        return image;
    }

    public String getClickToCall() {
        return clickToCall;
    }

    public String getClickToUrl() {
        return clickToUrl;
    }

    public String getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Promos{" +
                "id=" + id +
                ", lastUpdateDate=" + lastUpdateDate +
                ", caption='" + caption + '\'' +
                ", image=" + image +
                ", clickToCall='" + clickToCall + '\'' +
                ", clickToUrl='" + clickToUrl + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
