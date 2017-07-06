package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Points implements Parcelable {
    private final String type;
    private final String details;
    private final Integer total;
    private final Integer accumulatedTotal;
    private final Integer merchantId;

    protected Points(Parcel in) {
        type = in.readString();
        details = in.readString();
        total = in.readByte() == 0x00 ? null : in.readInt();
        accumulatedTotal = in.readByte() == 0x00 ? null : in.readInt();
        merchantId = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(details);
        if (total == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(total);
        }
        if (accumulatedTotal == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(accumulatedTotal);
        }
        if (merchantId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(merchantId);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Points> CREATOR = new Parcelable.Creator<Points>() {
        @Override
        public Points createFromParcel(Parcel in) {
            return new Points(in);
        }

        @Override
        public Points[] newArray(int size) {
            return new Points[size];
        }
    };

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getAccumulatedTotal() {
        return accumulatedTotal;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Points points = (Points) o;

        if (type != null ? !type.equals(points.type) : points.type != null) return false;
        if (details != null ? !details.equals(points.details) : points.details != null)
            return false;
        if (total != null ? !total.equals(points.total) : points.total != null) return false;
        if (accumulatedTotal != null ? !accumulatedTotal.equals(points.accumulatedTotal) : points.accumulatedTotal != null)
            return false;
        return merchantId != null ? merchantId.equals(points.merchantId) : points.merchantId == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (accumulatedTotal != null ? accumulatedTotal.hashCode() : 0);
        result = 31 * result + (merchantId != null ? merchantId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Points{" +
                "type='" + type + '\'' +
                ", details='" + details + '\'' +
                ", total=" + total +
                ", accumulatedTotal=" + accumulatedTotal +
                ", merchantId=" + merchantId +
                '}';
    }
}
