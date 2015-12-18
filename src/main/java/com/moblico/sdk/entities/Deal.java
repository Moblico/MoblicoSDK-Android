package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.Comparable;
import java.util.Date;

public class Deal implements Parcelable, Comparable<Deal> {
    private final long id;
    private final Date createDate;
    private final Date lastUpdateDate;
    private final String description;
    private final Date startDate;
    private final Date endDate;
    private final Image image;
    private final String legalese;
    private final String offerCode;
    private final String name;
    private final int redeemedCount;
    private final int numberOfUsesPerCode;
    private final String url;
    private final String urlName;
    private final boolean appRedemptionEnabled;
    private final String promoText;
    private final boolean goalEnabled;
    private final int currentGoalAmount;
    private final int currentGoalQuantity;
    private final int targetGoalAmount;
    private final int targetGoalQuantity;
    private final int totalMetGoals;
    private final int maximumMetGoals;


    protected Deal(Parcel in) {
        id = in.readLong();
        long tmpCreateDate = in.readLong();
        createDate = tmpCreateDate != -1 ? new Date(tmpCreateDate) : null;
        long tmpLastUpdateDate = in.readLong();
        lastUpdateDate = tmpLastUpdateDate != -1 ? new Date(tmpLastUpdateDate) : null;
        description = in.readString();
        long tmpStartDate = in.readLong();
        startDate = tmpStartDate != -1 ? new Date(tmpStartDate) : null;
        long tmpEndDate = in.readLong();
        endDate = tmpEndDate != -1 ? new Date(tmpEndDate) : null;
        image = (Image) in.readValue(Image.class.getClassLoader());
        legalese = in.readString();
        offerCode = in.readString();
        name = in.readString();
        redeemedCount = in.readInt();
        numberOfUsesPerCode = in.readInt();
        url = in.readString();
        urlName = in.readString();
        appRedemptionEnabled = in.readByte() != 0x00;
        promoText = in.readString();
        goalEnabled = in.readByte() != 0x00;
        currentGoalAmount = in.readInt();
        currentGoalQuantity = in.readInt();
        targetGoalAmount = in.readInt();
        targetGoalQuantity = in.readInt();
        totalMetGoals = in.readInt();
        maximumMetGoals = in.readInt();
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
        dest.writeString(description);
        dest.writeLong(startDate != null ? startDate.getTime() : -1L);
        dest.writeLong(endDate != null ? endDate.getTime() : -1L);
        dest.writeValue(image);
        dest.writeString(legalese);
        dest.writeString(offerCode);
        dest.writeString(name);
        dest.writeInt(redeemedCount);
        dest.writeInt(numberOfUsesPerCode);
        dest.writeString(url);
        dest.writeString(urlName);
        dest.writeByte((byte) (appRedemptionEnabled ? 0x01 : 0x00));
        dest.writeString(promoText);
        dest.writeByte((byte) (goalEnabled ? 0x01 : 0x00));
        dest.writeInt(currentGoalAmount);
        dest.writeInt(currentGoalQuantity);
        dest.writeInt(targetGoalAmount);
        dest.writeInt(targetGoalQuantity);
        dest.writeInt(totalMetGoals);
        dest.writeInt(maximumMetGoals);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Deal> CREATOR = new Parcelable.Creator<Deal>() {
        @Override
        public Deal createFromParcel(Parcel in) {
            return new Deal(in);
        }

        @Override
        public Deal[] newArray(int size) {
            return new Deal[size];
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

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Image getImage() {
        return image;
    }

    public String getLegalese() {
        return legalese;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public String getName() {
        return name;
    }

    public int getRedeemedCount() {
        return redeemedCount;
    }

    public int getNumberOfUsesPerCode() {
        return numberOfUsesPerCode;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlName() {
        return urlName;
    }

    public boolean isAppRedemptionEnabled() {
        return appRedemptionEnabled;
    }

    public String getPromoText() {
        return promoText;
    }

    public boolean isGoalEnabled() {
        return goalEnabled;
    }

    public int getCurrentGoalAmount() {
        return currentGoalAmount;
    }

    public int getCurrentGoalQuantity() {
        return currentGoalQuantity;
    }

    public int getTargetGoalAmount() {
        return targetGoalAmount;
    }

    public int getTargetGoalQuantity() {
        return targetGoalQuantity;
    }

    public int getTotalMetGoals() {
        return totalMetGoals;
    }

    public int getMaximumMetGoals() {
        return maximumMetGoals;
    }

    @Override
    public String toString() {
        return "Deal{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", image=" + image +
                ", legalese='" + legalese + '\'' +
                ", offerCode='" + offerCode + '\'' +
                ", name='" + name + '\'' +
                ", redeemedCount=" + redeemedCount +
                ", numberOfUsesPerCode=" + numberOfUsesPerCode +
                ", url='" + url + '\'' +
                ", urlName='" + urlName + '\'' +
                ", appRedemptionEnabled=" + appRedemptionEnabled +
                ", promoText='" + promoText + '\'' +
                ", goalEnabled=" + goalEnabled +
                ", currentGoalAmount=" + currentGoalAmount +
                ", currentGoalQuantity=" + currentGoalQuantity +
                ", targetGoalAmount=" + targetGoalAmount +
                ", targetGoalQuantity=" + targetGoalQuantity +
                ", totalMetGoals=" + totalMetGoals +
                ", maximumMetGoals=" + maximumMetGoals +
                '}';
    }

    public int compareTo(Deal o) {
        int results = this.getEndDate().compareTo(o.getEndDate());
        if (results != 0) {
            return results;
        }
        
        results = this.getName().compareToIgnoreCase(o.getName());

        if (results != 0) {
            return results;
        }

        // Requires API 19
        // return Long.compare(this.getId(), o.getId());

        return ((Long)this.getId()).compareTo((Long)o.getId());
    }

}
