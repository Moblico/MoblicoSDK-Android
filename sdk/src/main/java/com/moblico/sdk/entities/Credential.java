package com.moblico.sdk.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Credential implements Parcelable {
    private final long accountId;
    private final String accountName;
    private final String companyName;
    private final String username;
    private final String apiKey;
    private final String keyword;
    private final long parentAccountId;

    private Credential(Parcel in) {
        accountId = in.readLong();
        accountName = in.readString();
        companyName = in.readString();
        username = in.readString();
        apiKey = in.readString();
        keyword = in.readString();
        parentAccountId = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(accountId);
        dest.writeString(accountName);
        dest.writeString(companyName);
        dest.writeString(username);
        dest.writeString(apiKey);
        dest.writeString(keyword);
        dest.writeLong(parentAccountId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Credential> CREATOR = new Parcelable.Creator<Credential>() {
        @Override
        public Credential createFromParcel(Parcel in) {
            return new Credential(in);
        }

        @Override
        public Credential[] newArray(int size) {
            return new Credential[size];
        }
    };

    public long getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getUsername() {
        return username;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getKeyword() {
        return keyword;
    }

    public long getParentAccountId() {
        return parentAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Credential that = (Credential) o;

        if (accountId != that.accountId) return false;
        if (parentAccountId != that.parentAccountId) return false;
        if (accountName != null ? !accountName.equals(that.accountName) : that.accountName != null)
            return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null)
            return false;
        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        if (apiKey != null ? !apiKey.equals(that.apiKey) : that.apiKey != null) return false;
        return keyword != null ? keyword.equals(that.keyword) : that.keyword == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + (accountName != null ? accountName.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        result = 31 * result + (int) (parentAccountId ^ (parentAccountId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", username='" + username + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", keyword='" + keyword + '\'' +
                ", parentAccountId=" + parentAccountId +
                '}';
    }
}
