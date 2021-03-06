package com.moblico.sdk.entities;

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
    private String companyName;
    private String address1;
    private String address2;
    private String city;
    private String stateOrProvince;
    private String country;
    private String postalCode;
    private String dateOfBirth;
    private String age;
    private User.ContactPreferenceType contactPreference;
    private User.GenderType gender;
    private String locationId;
    private final Map<String, String> attributes = new HashMap<>();

    public UserBuilder() {
    }

    public UserBuilder(String username, String password) {
        this.username = username;
        this.password = password;
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
        companyName= emptyToNull(user.getCompanyName());
        address1 = emptyToNull(user.getAddress1());
        address2 = emptyToNull(user.getAddress2());
        city = emptyToNull(user.getCity());
        stateOrProvince = emptyToNull(user.getStateOrProvince());
        country = emptyToNull(user.getCountry());
        postalCode = emptyToNull(user.getPostalCode());
        dateOfBirth = emptyToNull(user.getDateOfBirth());
        age = emptyToNull(user.getAge());
        contactPreference = user.getContactPreference();
        gender = user.getGender();
        locationId = emptyToNull(user.getLocationId());
        attributes.putAll(user.getAttributes());
    }

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setOptinEmail(boolean optinEmail) {
        this.optinEmail = optinEmail;
        return this;
    }

    public UserBuilder setOptinPhone(boolean optinPhone) {
        this.optinPhone = optinPhone;
        return this;
    }

    public UserBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public UserBuilder setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public UserBuilder setAddress1(String address1) {
        this.address1 = address1;
        return this;
    }

    public UserBuilder setAddress2(String address2) {
        this.address2 = address2;
        return this;
    }

    public UserBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public UserBuilder setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
        return this;
    }

    public UserBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public UserBuilder setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public UserBuilder setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserBuilder setAge(String age) {
        this.age = age;
        return this;
    }

    public UserBuilder setContactPreference(User.ContactPreferenceType contactPreference) {
        this.contactPreference = contactPreference;
        return this;
    }

    public UserBuilder setGender(User.GenderType gender) {
        this.gender = gender;
        return this;
    }

    public UserBuilder setLocationId(String locationId) {
        this.locationId = locationId;
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
        return new User(username, password, phone, email, nickName, locale, firstName, lastName, companyName,
                address1, address2, city, stateOrProvince, country, postalCode, dateOfBirth, age, optinEmail,
                optinPhone, contactPreference, gender, locationId, attributes);
    }

    private static String emptyToNull(String input) {
        if (input != null && input.isEmpty()) {
            return null;
        }
        return input;
    }
}
