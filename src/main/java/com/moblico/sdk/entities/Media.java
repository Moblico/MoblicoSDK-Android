package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Media implements Parcelable {
    private final long id;
    private final Date createDate;
    private final Date lastUpdateDate;
    private final String name;
    private final String type;
    private final String description;
    private final String mimeType;
    private final String url;
    private final boolean shouldCache;
    private final long priority;
    private final String imageUrl;
    private final String thumbUrl;
    private final String externalId;
    private final Map<String, String> attributes;

    protected Media(Parcel in) {
        id = in.readLong();
        long tmpCreateDate = in.readLong();
        createDate = tmpCreateDate != -1 ? new Date(tmpCreateDate) : null;
        long tmpLastUpdateDate = in.readLong();
        lastUpdateDate = tmpLastUpdateDate != -1 ? new Date(tmpLastUpdateDate) : null;
        name = in.readString();
        type = in.readString();
        description = in.readString();
        mimeType = in.readString();
        url = in.readString();
        shouldCache = in.readByte() != 0x00;
        priority = in.readLong();
        imageUrl = in.readString();
        thumbUrl = in.readString();
        externalId = in.readString();
        // TODO: move this pattern to a base class if it is used much
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
        dest.writeLong(createDate != null ? createDate.getTime() : -1L);
        dest.writeLong(lastUpdateDate != null ? lastUpdateDate.getTime() : -1L);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeString(mimeType);
        dest.writeString(url);
        dest.writeByte((byte) (shouldCache ? 0x01 : 0x00));
        dest.writeLong(priority);
        dest.writeString(imageUrl);
        dest.writeString(thumbUrl);
        dest.writeString(externalId);
        dest.writeInt(attributes.size());
        for(Map.Entry<String,String> entry : attributes.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Media> CREATOR = new Parcelable.Creator<Media>() {
        @Override
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        @Override
        public Media[] newArray(int size) {
            return new Media[size];
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

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getUrl() {
        return url;
    }

    public boolean isShouldCache() {
        return shouldCache;
    }

    public long getPriority() {
        return priority;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getExternalId() {
        return externalId;
    }

    public boolean hasAttribute(final String attribute) {
        return attributes.containsKey(attribute);
    }

    public String getAttribute(final String attribute) {
        return attributes.get(attribute);
    }

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", url='" + url + '\'' +
                ", shouldCache=" + shouldCache +
                ", priority=" + priority +
                ", imageUrl='" + imageUrl + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", externalId='" + externalId + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}
