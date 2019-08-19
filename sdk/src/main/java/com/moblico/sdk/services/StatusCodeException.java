package com.moblico.sdk.services;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moblico.sdk.entities.Status;

import java.io.IOException;

public class StatusCodeException extends IOException {
    private final int mStatusCode;
    private final Status mStatus;

    public StatusCodeException(final String statusJson) {
        this(new Gson().fromJson(statusJson, Status.class));
    }

    public StatusCodeException(final Status status) {
        super(status.toString());
        mStatus = status;
        mStatusCode = status.getHttpStatus();
    }

    public StatusCodeException(final int statusCode) {
        super(Integer.toString(statusCode));
        mStatus = null;
        mStatusCode = statusCode;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    @Nullable
    public Status getStatus() {
        return mStatus;
    }
}
