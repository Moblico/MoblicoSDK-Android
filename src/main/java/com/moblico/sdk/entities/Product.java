package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Product implements Parcelable {

    private final long id;
    private final String title;
    private final String price;
    private final String legalese;
    private final String description;
    private final String sku;
    private final String url;
    private final String productType;
    private final String specifications;
    private final String salesRepEmail;
    private final String salesRepName;
    private final String distribution;
    private final String externalId;
    private final Date lastUpdateDate;
    private final Date introDate;
    private final Date revDate;
    private final Date expirationDate;
    private final boolean active;
    private final boolean discontinued;
    private final boolean instock;
    private final boolean revised;
    private final boolean emailable;
    private final boolean mustContactSalesRep;
    private final Media media;

    private Date readDate(Parcel in) {
        long tmpDate = in.readLong();
        return tmpDate != -1 ? new Date(tmpDate) : null;
    }

    private boolean readBoolean(Parcel in) {
        return in.readByte() != 0x00;
    }
    protected Product(Parcel in) {
        id = in.readLong();

        title = in.readString();
        price = in.readString();
        legalese = in.readString();
        description = in.readString();
        sku = in.readString();
        url = in.readString();
        productType = in.readString();
        specifications = in.readString();
        salesRepEmail = in.readString();
        salesRepName = in.readString();
        distribution = in.readString();
        externalId = in.readString();

        lastUpdateDate = readDate(in);
        introDate = readDate(in);
        revDate = readDate(in);
        expirationDate = readDate(in);

        active = readBoolean(in);
        discontinued = readBoolean(in);
        instock = readBoolean(in);
        revised = readBoolean(in);
        emailable = readBoolean(in);
        mustContactSalesRep = readBoolean(in);

        media = (Media) in.readValue(Media.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeLong(id);

        dest.writeString(title);
        dest.writeString(price);
        dest.writeString(legalese);
        dest.writeString(description);
        dest.writeString(sku);
        dest.writeString(url);
        dest.writeString(productType);
        dest.writeString(specifications);
        dest.writeString(salesRepEmail);
        dest.writeString(salesRepName);
        dest.writeString(distribution);
        dest.writeString(externalId);

        dest.writeLong(lastUpdateDate != null ? lastUpdateDate.getTime() : -1L);
        dest.writeLong(introDate != null ? introDate.getTime() : -1L);
        dest.writeLong(revDate != null ? revDate.getTime() : -1L);
        dest.writeLong(expirationDate != null ? expirationDate.getTime() : -1L);

        dest.writeByte((byte) (active ? 0x01 : 0x00));
        dest.writeByte((byte) (discontinued ? 0x01 : 0x00));
        dest.writeByte((byte) (instock ? 0x01 : 0x00));
        dest.writeByte((byte) (revised ? 0x01 : 0x00));
        dest.writeByte((byte) (emailable ? 0x01 : 0x00));
        dest.writeByte((byte) (mustContactSalesRep ? 0x01 : 0x00));

        dest.writeValue(media);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getLegalese() {
        return legalese;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    public String getUrl() {
        return url;
    }

    public String getProductType() {
        return productType;
    }

    public String getSpecifications() {
        return specifications;
    }

    public String getSalesRepEmail() {
        return salesRepEmail;
    }

    public String getSalesRepName() {
        return salesRepName;
    }

    public String getDistribution() {
        return distribution;
    }

    public String getExternalId() {
        return externalId;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Date getIntroDate() {
        return introDate;
    }

    public Date getRevDate() {
        return revDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isDiscontinued() {
        return discontinued;
    }

    public boolean isInstock() {
        return instock;
    }

    public boolean isRevised() {
        return revised;
    }

    public boolean isEmailable() {
        return emailable;
    }

    public boolean getMustContactSalesRep() {
        return mustContactSalesRep;
    }

    public Media getMedia() {
        return media;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", legalese='" + legalese + '\'' +
                ", description='" + description + '\'' +
                ", sku='" + sku + '\'' +
                ", url='" + url + '\'' +
                ", productType='" + productType + '\'' +
                ", specifications='" + specifications + '\'' +
                ", salesRepEmail='" + salesRepEmail + '\'' +
                ", salesRepName='" + salesRepName + '\'' +
                ", distribution='" + distribution + '\'' +
                ", externalId='" + externalId + '\'' +
                ", lastUpdateDate=" + lastUpdateDate +
                ", introDate=" + introDate +
                ", revDate=" + revDate +
                ", expirationDate=" + expirationDate +
                ", active=" + active +
                ", discontinued=" + discontinued +
                ", instock=" + instock +
                ", revised=" + revised +
                ", emailable=" + emailable +
                ", mustContactSalesRep=" + mustContactSalesRep +
                ", media=" + media +
                '}';
    }
}
