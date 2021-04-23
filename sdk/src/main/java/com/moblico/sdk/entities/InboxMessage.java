package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class InboxMessage implements Parcelable {
    private static class Payload implements Parcelable {
        private final ArrayList<Long> notifications;
        private final ArrayList<Long> deals;
        private final long messageId;

        Payload(Parcel in) {
            if (in.readByte() == 0x01) {
                notifications = new ArrayList<Long>();
                in.readList(notifications, Long.class.getClassLoader());
            } else {
                notifications = null;
            }
            if (in.readByte() == 0x01) {
                deals = new ArrayList<Long>();
                in.readList(deals, Long.class.getClassLoader());
            } else {
                deals = null;
            }
            messageId = in.readLong();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (notifications == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeList(notifications);
            }
            if (deals == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeList(deals);
            }
            dest.writeLong(messageId);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<Payload> CREATOR = new Parcelable.Creator<Payload>() {
            @Override
            public Payload createFromParcel(Parcel in) {
                return new Payload(in);
            }

            @Override
            public Payload[] newArray(int size) {
                return new Payload[size];
            }
        };

        @Override
        public String toString() {
            return "Payload{" +
                    "notifications=" + notifications +
                    ", deals=" + deals +
                    ", messageId=" + messageId +
                    '}';
        }
    }

    private final long id;
    private final String alertText;
    private final String titleText;
    private final String messageText;
    private final String passthru;
    private final Payload payload;
    private String state;
    private final Date dateCreated;

    private InboxMessage(Parcel in) {
        id = in.readLong();
        alertText = in.readString();
        titleText = in.readString();
        messageText = in.readString();
        passthru = in.readString();
        payload = (Payload) in.readValue(Payload.class.getClassLoader());
        state = in.readString();
        long tmpDateCreated = in.readLong();
        dateCreated = tmpDateCreated != -1 ? new Date(tmpDateCreated) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(alertText);
        dest.writeString(titleText);
        dest.writeString(messageText);
        dest.writeString(passthru);
        dest.writeValue(payload);
        dest.writeString(state);
        dest.writeLong(dateCreated != null ? dateCreated.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<InboxMessage> CREATOR = new Parcelable.Creator<InboxMessage>() {
        @Override
        public InboxMessage createFromParcel(Parcel in) {
            return new InboxMessage(in);
        }

        @Override
        public InboxMessage[] newArray(int size) {
            return new InboxMessage[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getAlertText() {
        return alertText;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public boolean isNew() {
        return !("OPENED".equalsIgnoreCase(state));
    }

    public void setOpened() {
        state = "OPENED";
    }

    public ArrayList<Long> getDeals() {
        if (payload == null || payload.deals == null) {
            return new ArrayList<Long>();
        }
        return payload.deals;
    }

    public ArrayList<Long> getNotifications() {
        if (payload == null || payload.notifications == null) {
            return new ArrayList<Long>();
        }
        return payload.notifications;
    }

    @Override
    public String toString() {
        return "InboxMessage{" +
                "id=" + id +
                ", alertText='" + alertText + '\'' +
                ", titleText='" + titleText + '\'' +
                ", messageText='" + messageText + '\'' +
                ", passthru='" + passthru + '\'' +
                ", payload=" + payload +
                ", state='" + state + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
