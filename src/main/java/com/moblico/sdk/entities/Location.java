package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Location implements Parcelable {
    private final long id;
    private final Date createDate;
    private final Date lastUpdateDate;
    private final String type;
    private final boolean active;
    private final long merchantId;
    private final String name;
    private final String description;
    private final String address1;
    private final String address2;
    private final String city;
    private final String county;
    private final String stateOrProvince;
    private final String phone;
    private final String email;
    private final String postalCode;
    private final String country;
    private final double latitude;
    private final double longitude;
    private final double distance;
    private final String url;
    private final String contactName;
    private final String externalId;
    private final String locale;
    private final boolean geoNotificationEnabled;
    private final boolean geoEnterNotificationEnabled;
    private final int geoFenceRadius;
    private final String geoEnterNotificationText;
    private final boolean checkinEnabled;
    private final int checkinRadius;
    private final String beaconIdentifier;
    private final Map<String, String> attributes;

    protected Location(Parcel in) {
        id = in.readLong();
        long tmpCreateDate = in.readLong();
        createDate = tmpCreateDate != -1 ? new Date(tmpCreateDate) : null;
        long tmpLastUpdateDate = in.readLong();
        lastUpdateDate = tmpLastUpdateDate != -1 ? new Date(tmpLastUpdateDate) : null;
        type = in.readString();
        active = in.readByte() != 0x00;
        merchantId = in.readLong();
        name = in.readString();
        description = in.readString();
        address1 = in.readString();
        address2 = in.readString();
        city = in.readString();
        county = in.readString();
        stateOrProvince = in.readString();
        phone = in.readString();
        email = in.readString();
        postalCode = in.readString();
        country = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        distance = in.readDouble();
        url = in.readString();
        contactName = in.readString();
        externalId = in.readString();
        locale = in.readString();
        geoNotificationEnabled = in.readByte() != 0x00;
        geoEnterNotificationEnabled = in.readByte() != 0x00;
        geoFenceRadius = in.readInt();
        geoEnterNotificationText = in.readString();
        checkinEnabled = in.readByte() != 0x00;
        checkinRadius = in.readInt();
        beaconIdentifier = in.readString();
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
        dest.writeString(type);
        dest.writeByte((byte) (active ? 0x01 : 0x00));
        dest.writeLong(merchantId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(address1);
        dest.writeString(address2);
        dest.writeString(city);
        dest.writeString(county);
        dest.writeString(stateOrProvince);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(postalCode);
        dest.writeString(country);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(distance);
        dest.writeString(url);
        dest.writeString(contactName);
        dest.writeString(externalId);
        dest.writeString(locale);
        dest.writeByte((byte) (geoNotificationEnabled ? 0x01 : 0x00));
        dest.writeByte((byte) (geoEnterNotificationEnabled ? 0x01 : 0x00));
        dest.writeInt(geoFenceRadius);
        dest.writeString(geoEnterNotificationText);
        dest.writeByte((byte) (checkinEnabled ? 0x01 : 0x00));
        dest.writeInt(checkinRadius);
        dest.writeString(beaconIdentifier);
        dest.writeInt(attributes.size());
        for(Map.Entry<String,String> entry : attributes.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
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

    public String getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDistance() {
        return distance;
    }

    public String getUrl() {
        return url;
    }

    public String getContactName() {
        return contactName;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getLocale() {
        return locale;
    }

    public boolean isGeoNotificationEnabled() {
        return geoNotificationEnabled;
    }

    public boolean isGeoEnterNotificationEnabled() {
        return geoEnterNotificationEnabled;
    }

    public int getGeoFenceRadius() {
        return geoFenceRadius;
    }

    public String getGeoEnterNotificationText() {
        return geoEnterNotificationText;
    }

    public boolean isCheckinEnabled() {
        return checkinEnabled;
    }

    public int getCheckinRadius() {
        return checkinRadius;
    }

    public String getBeaconIdentifier() {
        return beaconIdentifier;
    }

    public boolean hasAttribute(final String attribute) {
        return attributes.containsKey(attribute);
    }

    public String getAttribute(final String attribute) {
        return attributes.get(attribute);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", type='" + type + '\'' +
                ", active=" + active +
                ", merchantId=" + merchantId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", stateOrProvince='" + stateOrProvince + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distance=" + distance +
                ", url='" + url + '\'' +
                ", contactName='" + contactName + '\'' +
                ", externalId='" + externalId + '\'' +
                ", locale='" + locale + '\'' +
                ", geoNotificationEnabled=" + geoNotificationEnabled +
                ", geoEnterNotificationEnabled=" + geoEnterNotificationEnabled +
                ", geoFenceRadius=" + geoFenceRadius +
                ", geoEnterNotificationText='" + geoEnterNotificationText + '\'' +
                ", checkinEnabled=" + checkinEnabled +
                ", checkinRadius=" + checkinRadius +
                ", beaconIdentifier=" + beaconIdentifier +
                ", attributes=" + attributes +
                '}';
    }
}
