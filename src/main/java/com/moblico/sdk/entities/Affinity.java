package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Affinity implements Parcelable {
    private final long id;
    private final Date createDate;
    private final Date lastUpdateDate;
    private final long affinityActionId;
    private final String name;
    private final String description;
    private final Date startDate;
    private final Date endDate;
    private final long pointTypeId;
    private final Long rewardId;
    private final String optional;
    private final long points;
    private final long userLimit;
    private final Long eventId;
    private final Long locationId;
    private final Long questionAnswerId;
    private final Long groupId;
    private final Long recipientGroupId;
    private final String affinityActionName;

    protected Affinity(Parcel in) {
        id = in.readLong();
        long tmpCreateDate = in.readLong();
        createDate = tmpCreateDate != -1 ? new Date(tmpCreateDate) : null;
        long tmpLastUpdateDate = in.readLong();
        lastUpdateDate = tmpLastUpdateDate != -1 ? new Date(tmpLastUpdateDate) : null;
        affinityActionId = in.readLong();
        name = in.readString();
        description = in.readString();
        long tmpStartDate = in.readLong();
        startDate = tmpStartDate != -1 ? new Date(tmpStartDate) : null;
        long tmpEndDate = in.readLong();
        endDate = tmpEndDate != -1 ? new Date(tmpEndDate) : null;
        pointTypeId = in.readLong();
        rewardId = in.readByte() == 0x00 ? null : in.readLong();
        optional = in.readString();
        points = in.readLong();
        userLimit = in.readLong();
        eventId = in.readByte() == 0x00 ? null : in.readLong();
        locationId = in.readByte() == 0x00 ? null : in.readLong();
        questionAnswerId = in.readByte() == 0x00 ? null : in.readLong();
        groupId = in.readByte() == 0x00 ? null : in.readLong();
        recipientGroupId = in.readByte() == 0x00 ? null : in.readLong();
        affinityActionName = in.readString();
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
        dest.writeLong(affinityActionId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeLong(startDate != null ? startDate.getTime() : -1L);
        dest.writeLong(endDate != null ? endDate.getTime() : -1L);
        dest.writeLong(pointTypeId);
        if (rewardId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(rewardId);
        }
        dest.writeString(optional);
        dest.writeLong(points);
        dest.writeLong(userLimit);
        if (eventId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(eventId);
        }
        if (locationId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(locationId);
        }
        if (questionAnswerId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(questionAnswerId);
        }
        if (groupId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(groupId);
        }
        if (recipientGroupId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(recipientGroupId);
        }
        dest.writeString(affinityActionName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Affinity> CREATOR = new Parcelable.Creator<Affinity>() {
        @Override
        public Affinity createFromParcel(Parcel in) {
            return new Affinity(in);
        }

        @Override
        public Affinity[] newArray(int size) {
            return new Affinity[size];
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

    public long getAffinityActionId() {
        return affinityActionId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public long getPointTypeId() {
        return pointTypeId;
    }

    public Long getRewardId() {
        return rewardId;
    }

    public String getOptional() {
        return optional;
    }

    public long getPoints() {
        return points;
    }

    public long getUserLimit() {
        return userLimit;
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public Long getQuestionAnswerId() {
        return questionAnswerId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Long getRecipientGroupId() {
        return recipientGroupId;
    }

    public String getAffinityActionName() {
        return affinityActionName;
    }

    @Override
    public String toString() {
        return "Affinity{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", affinityActionId=" + affinityActionId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", pointTypeId=" + pointTypeId +
                ", rewardId=" + rewardId +
                ", optional='" + optional + '\'' +
                ", points=" + points +
                ", userLimit=" + userLimit +
                ", eventId=" + eventId +
                ", locationId=" + locationId +
                ", questionAnswerId=" + questionAnswerId +
                ", groupId=" + groupId +
                ", recipientGroupId=" + recipientGroupId +
                ", affinityActionName='" + affinityActionName + '\'' +
                '}';
    }
}
