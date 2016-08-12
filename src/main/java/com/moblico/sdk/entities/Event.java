package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Event implements Parcelable {
    private final long id;
    private final Date createDate;
    private final Date lastUpdateDate;
    private final long parentId;
    private final String externalId;
    private final String type;
    private final String name;
    private final String description;
    private final String timeZone;
    private final String phone;
    private final String email;
    private final String url;
    private final Date startDate;
    private final String startTime;
    private final Date endDate;
    private final String endTime;
    private final String rsvpName;
    private final String rsvpEmail;
    private final String rsvpPhone;
    private final String rsvpUrl;
    private final Map<String, String> attributes;

    protected Event(Parcel in) {
        id = in.readLong();
        long tmpCreateDate = in.readLong();
        createDate = tmpCreateDate != -1 ? new Date(tmpCreateDate) : null;
        long tmpLastUpdateDate = in.readLong();
        lastUpdateDate = tmpLastUpdateDate != -1 ? new Date(tmpLastUpdateDate) : null;
        parentId = in.readLong();
        externalId = in.readString();
        type = in.readString();
        name = in.readString();
        description = in.readString();
        timeZone = in.readString();
        phone = in.readString();
        email = in.readString();
        url = in.readString();
        long tmpStartDate = in.readLong();
        startDate = tmpStartDate != -1 ? new Date(tmpStartDate) : null;
        startTime = in.readString();
        long tmpEndDate = in.readLong();
        endDate = tmpEndDate != -1 ? new Date(tmpEndDate) : null;
        endTime = in.readString();
        rsvpName = in.readString();
        rsvpEmail = in.readString();
        rsvpPhone = in.readString();
        rsvpUrl = in.readString();
        // TODO: move this pattern to a base class if it is used much
        final int size = in.readInt();
        attributes = new HashMap<>(size);
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
        dest.writeLong(parentId);
        dest.writeString(externalId);
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(timeZone);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(url);
        dest.writeLong(startDate != null ? startDate.getTime() : -1L);
        dest.writeString(startTime);
        dest.writeLong(endDate != null ? endDate.getTime() : -1L);
        dest.writeString(endTime);
        dest.writeString(rsvpName);
        dest.writeString(rsvpEmail);
        dest.writeString(rsvpPhone);
        dest.writeString(rsvpUrl);
        if (attributes == null) {
            dest.writeInt(0);
        } else {
            dest.writeInt(attributes.size());
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                dest.writeString(entry.getKey());
                dest.writeString(entry.getValue());
            }
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getRsvpName() {
        return rsvpName;
    }

    public String getRsvpEmail() {
        return rsvpEmail;
    }

    public String getRsvpPhone() {
        return rsvpPhone;
    }

    public String getRsvpUrl() {
        return rsvpUrl;
    }

    public String getExternalId() {
        return externalId;
    }

    public long getParentId() {
        return parentId;
    }

    public boolean hasAttribute(final String attribute) {
        if (attributes == null) {
            return false;
        }
        return attributes.containsKey(attribute);
    }

    public String getAttribute(final String attribute) {
        if (attributes == null) {
            return null;
        }
        return attributes.get(attribute);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", parentId=" + parentId +
                ", externalId='" + externalId + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", url='" + url + '\'' +
                ", startDate=" + startDate +
                ", startTime='" + startTime + '\'' +
                ", endDate=" + endDate +
                ", endTime='" + endTime + '\'' +
                ", rsvpName='" + rsvpName + '\'' +
                ", rsvpEmail='" + rsvpEmail + '\'' +
                ", rsvpPhone='" + rsvpPhone + '\'' +
                ", rsvpUrl='" + rsvpUrl + '\'' +
                ", attributes=" + attributes +
                '}';
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return id == event.id;
    }
}
