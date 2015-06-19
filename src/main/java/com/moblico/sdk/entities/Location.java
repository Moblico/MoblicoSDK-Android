package com.moblico.sdk.entities;

import java.util.Date;
import java.util.Map;

public class Location {
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
    private final Map<String, String> attributes;

    private Location(final long id, final Date createDate, final Date lastUpdateDate, final String type,
                     final boolean active, final long merchantId, final String name, final String description,
                     final String address1, final String address2, final String city, final String county,
                     final String stateOrProvince, final String phone, final String email, final String postalCode,
                     final String country, final double latitude, final double longitude, final double distance,
                     final String url, final String contactName, final String externalId, final String locale,
                     final boolean geoNotificationEnabled, final boolean geoEnterNotificationEnabled,
                     final int geoFenceRadius, final String geoEnterNotificationText,
                     final boolean checkinEnabled, final int checkinRadius, final Map<String, String> attributes) {
        this.id = id;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.type = type;
        this.active = active;
        this.merchantId = merchantId;
        this.name = name;
        this.description = description;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.county = county;
        this.stateOrProvince = stateOrProvince;
        this.phone = phone;
        this.email = email;
        this.postalCode = postalCode;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.url = url;
        this.contactName = contactName;
        this.externalId = externalId;
        this.locale = locale;
        this.geoNotificationEnabled = geoNotificationEnabled;
        this.geoEnterNotificationEnabled = geoEnterNotificationEnabled;
        this.geoFenceRadius = geoFenceRadius;
        this.geoEnterNotificationText = geoEnterNotificationText;
        this.checkinEnabled = checkinEnabled;
        this.checkinRadius = checkinRadius;
        this.attributes = attributes;
    }

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
                ", attributes=" + attributes +
                '}';
    }
}
