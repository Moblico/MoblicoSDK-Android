package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User implements Parcelable {

    public enum GenderType {
        @SerializedName("")
        UNDECLARED,
        @SerializedName("Male")
        MALE,
        @SerializedName("Female")
        FEMALE
    }

    public enum ContactPreferenceType {
        NONE,
        SMS,
        EMAIL,
        BOTH
    }

    private final String username;
    private final String password;
    private final boolean optinEmail;
    private final boolean optinPhone;
    private final String phone;
    private final String email;
    private final String nickName;
    private final String locale;
    private final String firstName;
    private final String lastName;
    private final String companyName;
    private final String address1;
    private final String address2;
    private final String city;
    private final String stateOrProvince;
    private final String country;
    private final String postalCode;
    private final String dateOfBirth;
    private final String age;
    private final Date createDate;
    private final Date lastUpdateDate;
    private final ContactPreferenceType contactPreference;
    private final GenderType gender;
    private final String locationId;
    private final String externalId;
    private final long merchantId;
    private final Map<String, String> attributes;

    private transient Map<String, String> params = null;

    User(String username, String password, String phone, String email, String nickName,
                 String locale, String firstName, String lastName, String companyName, String address1, String address2,
                 String city, String stateOrProvince, String country, String postalCode,
                 String dateOfBirth, String age, boolean optinEmail, boolean optinPhone,
                 ContactPreferenceType contactPreference, GenderType gender, String locationId,
                 Map<String, String> attributes) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.nickName = nickName;
        this.locale = locale;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.stateOrProvince = stateOrProvince;
        this.country = country;
        this.postalCode = postalCode;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.optinEmail = optinEmail;
        this.optinPhone = optinPhone;
        this.contactPreference = contactPreference;
        this.gender = gender;
        this.locationId = locationId;
        this.externalId = null;
        this.merchantId = 0;
        this.createDate = null;
        this.lastUpdateDate = null;
        this.attributes = new HashMap<>();
        if (attributes != null) {
            this.attributes.putAll(attributes);
        }
    }

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        optinEmail = in.readByte() != 0x00;
        optinPhone = in.readByte() != 0x00;
        phone = in.readString();
        email = in.readString();
        nickName = in.readString();
        locale = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        companyName = in.readString();
        address1 = in.readString();
        address2 = in.readString();
        city = in.readString();
        stateOrProvince = in.readString();
        country = in.readString();
        postalCode = in.readString();
        dateOfBirth = in.readString();
        age = in.readString();
        long tmpCreateDate = in.readLong();
        createDate = tmpCreateDate != -1 ? new Date(tmpCreateDate) : null;
        long tmpLastUpdateDate = in.readLong();
        lastUpdateDate = tmpLastUpdateDate != -1 ? new Date(tmpLastUpdateDate) : null;
        contactPreference = (ContactPreferenceType) in.readValue(ContactPreferenceType.class.getClassLoader());
        gender = (GenderType) in.readValue(GenderType.class.getClassLoader());
        locationId = in.readString();
        externalId = in.readString();
        merchantId = in.readLong();
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
        dest.writeString(username);
        dest.writeString(password);
        dest.writeByte((byte) (optinEmail ? 0x01 : 0x00));
        dest.writeByte((byte) (optinPhone ? 0x01 : 0x00));
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(nickName);
        dest.writeString(locale);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(companyName);
        dest.writeString(address1);
        dest.writeString(address2);
        dest.writeString(city);
        dest.writeString(stateOrProvince);
        dest.writeString(country);
        dest.writeString(postalCode);
        dest.writeString(dateOfBirth);
        dest.writeString(age);
        dest.writeLong(createDate != null ? createDate.getTime() : -1L);
        dest.writeLong(lastUpdateDate != null ? lastUpdateDate.getTime() : -1L);
        dest.writeValue(contactPreference);
        dest.writeValue(gender);
        dest.writeString(locationId);
        dest.writeString(externalId);
        dest.writeLong(merchantId);
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

    public String getPassword() {
        return password;
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

    public String getCompanyName() {
        return companyName;
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

    public String getAge() {
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

    public String getLocationId() {
        return locationId;
    }

    public String getExternalId() {
        return externalId;
    }

    public long getMerchantId() {
        return merchantId;
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
                ", companyName='" + companyName + '\'' +
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
                ", locationId=" + locationId +
                ", merchantId=" + merchantId +
                ", attributes=" + attributes +
                '}';
    }

    public Map<String, String> getParams() {
        if (params != null) {
            // A user doesn't change.  Lazily-load this map.
            return params;
        }

        // Use reflection to convert all the fields in User to <K:V> pairs.  This way, when
        // a field is added to user, we get it for free.  There might be easier ways to do
        // this with existing libraries...
        params = new HashMap<>();
        params.putAll(getAttributes());
        for(Field field : User.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.getName().equals("attributes") // Skip attributes!  They were already added above.
                        || java.lang.reflect.Modifier.isStatic(field.getModifiers()) // Skip static fields!
                        || java.lang.reflect.Modifier.isTransient(field.getModifiers()) // Skip transient fields!
                        ) {
                    continue;
                }

                Object value = field.get(this);
                if (field.getType().equals(boolean.class)) {
                    value = field.getBoolean(this) ? "YES" : "NO";
                }

                if (value != null) {
                    params.put(field.getName(), value.toString());
                }
            } catch (IllegalAccessException ignored) {
            }
        }
        return params;
    }
}
