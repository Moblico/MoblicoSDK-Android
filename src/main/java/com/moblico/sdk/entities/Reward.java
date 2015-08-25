package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Reward extends Deal {
    private final long points;
    private final long numberOfPurchases;
    private final long maxPurchases;
    private final Date startPurchaseDate;
    private final Date endPurchaseDate;
    private final boolean cardRedemptionEnabled;

    protected Reward(Parcel in) {
        super(in);
        points = in.readLong();
        numberOfPurchases = in.readLong();
        maxPurchases = in.readLong();
        long tmpStartPurchaseDate = in.readLong();
        startPurchaseDate = tmpStartPurchaseDate != -1 ? new Date(tmpStartPurchaseDate) : null;
        long tmpEndPurchaseDate = in.readLong();
        endPurchaseDate = tmpEndPurchaseDate != -1 ? new Date(tmpEndPurchaseDate) : null;
        cardRedemptionEnabled = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(points);
        dest.writeLong(numberOfPurchases);
        dest.writeLong(maxPurchases);
        dest.writeLong(startPurchaseDate != null ? startPurchaseDate.getTime() : -1L);
        dest.writeLong(endPurchaseDate != null ? endPurchaseDate.getTime() : -1L);
        dest.writeByte((byte) (cardRedemptionEnabled ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Reward> CREATOR = new Parcelable.Creator<Reward>() {
        @Override
        public Reward createFromParcel(Parcel in) {
            return new Reward(in);
        }

        @Override
        public Reward[] newArray(int size) {
            return new Reward[size];
        }
    };

    public long getPoints() {
        return points;
    }

    public long getNumberOfPurchases() {
        return numberOfPurchases;
    }

    public long getMaxPurchases() {
        return maxPurchases;
    }

    public Date getStartPurchaseDate() {
        return startPurchaseDate;
    }

    public Date getEndPurchaseDate() {
        return endPurchaseDate;
    }

    public boolean isCardRedemptionEnabled() {
        return cardRedemptionEnabled;
    }

    /**
     * The number of redemptions availble for this reward; calculated by:
     *
     *      numberOfPurchases * numberOfUsesPerCode - redeemedCount
     */
    public long availableRedeptions() {
        if (getNumberOfPurchases() <= 0) {
            return 0;
        } else if (getNumberOfUsesPerCode() <= 0) {
            return Long.MAX_VALUE;
        }
        return getNumberOfPurchases() * getNumberOfUsesPerCode() - getRedeemedCount();
    }

    /**
     * Can this reward be redeemed (e.g. does not need to be purchased).
     */
    public boolean isRedeemable() {
        return availableRedeptions() > 0;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "baseDeal = " + super.toString() +
                "points=" + points +
                ", numberOfPurchases=" + numberOfPurchases +
                ", maxPurchases=" + maxPurchases +
                ", startPurchaseDate=" + startPurchaseDate +
                ", endPurchaseDate=" + endPurchaseDate +
                ", cardRedemptionEnabled=" + cardRedemptionEnabled +
                '}';
    }

    public int compareTo(Reward o) {
        boolean thisIsRedeemable = this.isRedeemable();

        // Required API 19
        // int results = Boolean.compare(o.isRedeemable(), thisIsRedeemable);
        int results = ((Boolean)o.isRedeemable()).compareTo((Boolean)thisIsRedeemable);

        if (results != 0) {
            return results;
        }

        if (!thisIsRedeemable) {
            // Requires API 19
            // results = Long.compare(this.getPoints(), o.getPoints());
            results = ((Long)this.getPoints()).compareTo((Long)o.getPoints());
        }

        if (results != 0) {
            return results;
        }

        return super.compareTo(o);
    }
}
