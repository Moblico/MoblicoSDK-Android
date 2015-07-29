package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User implements Parcelable {
    public static enum GenderType {
        @SerializedName("")
        UNDECLARED,
        MALE,
        FEMALE
    }

    public static enum ContactPreferenceType {
        NONE,
        SMS,
        EMAIL,
        BOTH
    }

    private final String username;
    private final boolean optinEmail;
    private final boolean optinPhone;
    private final String phone;
    private final String email;
    private final String nickName;
    private final String locale;
    private final String firstName;
    private final String lastName;
    private final String address1;
    private final String address2;
    private final String city;
    private final String stateOrProvince;
    private final String country;
    private final String postalCode;
    private final String dateOfBirth;
    private final int age;
    private final Date createDate;
    private final Date lastUpdateDate;
    private final ContactPreferenceType contactPreference;
    private final GenderType gender;
    private final Map<String, String> attributes;

    protected User(Parcel in) {
        username = in.readString();
        optinEmail = in.readByte() != 0x00;
        optinPhone = in.readByte() != 0x00;
        phone = in.readString();
        email = in.readString();
        nickName = in.readString();
        locale = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        address1 = in.readString();
        address2 = in.readString();
        city = in.readString();
        stateOrProvince = in.readString();
        country = in.readString();
        postalCode = in.readString();
        dateOfBirth = in.readString();
        age = in.readInt();
        long tmpCreateDate = in.readLong();
        createDate = tmpCreateDate != -1 ? new Date(tmpCreateDate) : null;
        long tmpLastUpdateDate = in.readLong();
        lastUpdateDate = tmpLastUpdateDate != -1 ? new Date(tmpLastUpdateDate) : null;
        contactPreference = (ContactPreferenceType) in.readValue(ContactPreferenceType.class.getClassLoader());
        gender = (GenderType) in.readValue(GenderType.class.getClassLoader());
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
        dest.writeString(username);
        dest.writeByte((byte) (optinEmail ? 0x01 : 0x00));
        dest.writeByte((byte) (optinPhone ? 0x01 : 0x00));
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(nickName);
        dest.writeString(locale);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(address1);
        dest.writeString(address2);
        dest.writeString(city);
        dest.writeString(stateOrProvince);
        dest.writeString(country);
        dest.writeString(postalCode);
        dest.writeString(dateOfBirth);
        dest.writeInt(age);
        dest.writeLong(createDate != null ? createDate.getTime() : -1L);
        dest.writeLong(lastUpdateDate != null ? lastUpdateDate.getTime() : -1L);
        dest.writeValue(contactPreference);
        dest.writeValue(gender);
        dest.writeInt(attributes.size());
        for(Map.Entry<String,String> entry : attributes.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };



    public String getUsername() {
        return username;
    }

    public boolean isOptinEmail() {
        return optinEmail;
    }

    public boolean isOptinPhone() {
        return optinPhone;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public String getLocale() {
        return locale;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public ContactPreferenceType getContactPreference() {
        return contactPreference;
    }

    public GenderType getGender() {
        return gender;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", optinEmail=" + optinEmail +
                ", optinPhone=" + optinPhone +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", locale='" + locale + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", stateOrProvince='" + stateOrProvince + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", age=" + age +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", contactPreference=" + contactPreference +
                ", gender=" + gender +
                ", attributes=" + attributes +
                '}';
    }
}
