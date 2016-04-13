package com.moblico.sdk.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserBuilder {
    private String username;
    private String password;
    private boolean optinEmail;
    private boolean optinPhone;
    private String phone;
    private String email;
    private String nickName;
    private String locale;
    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;
    private String dateOfBirth;
    private String age;
    private Date createDate;
    private Date lastUpdateDate;
    private User.ContactPreferenceType contactPreference;
    private User.GenderType gender;
    private String locationId;
    private String externalId;
    private Map<String, String> attributes = new HashMap<>();

    public UserBuilder() {
    }

    public UserBuilder(User user) {
        username = emptyToNull(user.getUsername());
        password = emptyToNull(user.getPassword());
        optinEmail = user.isOptinEmail();
        optinPhone = user.isOptinPhone();
        phone = emptyToNull(user.getPhone());
        email = emptyToNull(user.getEmail());
        nickName = emptyToNull(user.getNickName());
        locale = emptyToNull(user.getLocale());
        firstName = emptyToNull(user.getFirstName());
        lastName = emptyToNull(user.getLastName());
        address1 = emptyToNull(user.getAddress1());
        address2 = emptyToNull(user.getAddress2());
        city = emptyToNull(user.getCity());
        stateOrProvince = emptyToNull(user.getStateOrProvince());
        country = emptyToNull(user.getCountry());
        postalCode = emptyToNull(user.getPostalCode());
        dateOfBirth = emptyToNull(user.getDateOfBirth());
        age = emptyToNull(user.getAge());
        createDate = user.getCreateDate();
        lastUpdateDate = user.getLastUpdateDate();
        contactPreference = user.getContactPreference();
        gender = user.getGender();
        locationId = emptyToNull(user.getLocationId());
        externalId = emptyToNull(user.getExternalId());
        attributes.putAll(user.getAttributes());
    }

    public String getUsername() {
        return username;
    }

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isOptinEmail() {
        return optinEmail;
    }

    public UserBuilder setOptinEmail(boolean optinEmail) {
        this.optinEmail = optinEmail;
        return this;
    }

    public boolean isOptinPhone() {
        return optinPhone;
    }

    public UserBuilder setOptinPhone(boolean optinPhone) {
        this.optinPhone = optinPhone;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public UserBuilder setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getLocale() {
        return locale;
    }

    public UserBuilder setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress1() {
        return address1;
    }

    public UserBuilder setAddress1(String address1) {
        this.address1 = address1;
        return this;
    }

    public String getAddress2() {
        return address2;
    }

    public UserBuilder setAddress2(String address2) {
        this.address2 = address2;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public UserBuilder setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public UserBuilder setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public UserBuilder setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public String getAge() {
        return age;
    }

    public UserBuilder setAge(String age) {
        this.age = age;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public UserBuilder setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public UserBuilder setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    public User.ContactPreferenceType getContactPreference() {
        return contactPreference;
    }

    public UserBuilder setContactPreference(User.ContactPreferenceType contactPreference) {
        this.contactPreference = contactPreference;
        return this;
    }

    public User.GenderType getGender() {
        return gender;
    }

    public UserBuilder setGender(User.GenderType gender) {
        this.gender = gender;
        return this;
    }

    public String getLocationId() {
        return locationId;
    }

    public UserBuilder setLocationId(String locationId) {
        this.locationId = locationId;
        return this;
    }

    public String getExternalId() {
        return externalId;
    }

    public UserBuilder setExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public UserBuilder addAttribute(String name, String value) {
        attributes.put(name, value);
        return this;
    }

    public UserBuilder removeAttribute(String name) {
        attributes.remove(name);
        return this;
    }

    public User build() {
        return new User(username, password, phone, email, nickName, locale, firstName, lastName, address1,
                address2, city, stateOrProvince, country, postalCode, dateOfBirth, age, optinEmail,
                optinPhone, contactPreference, gender, locationId, attributes);
    }

    private static String emptyToNull(String input) {
        if (input != null && input.isEmpty()) {
            return null;
        }
        return input;
    }
}
