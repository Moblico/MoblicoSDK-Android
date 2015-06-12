package com.moblico.sdk.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moblico.sdk.entities.Status;

import java.io.IOException;

public class StatusCodeException extends IOException {
    private final int mStatusCode;

    public StatusCodeException(final String statusJson) {
        this(new Gson().fromJson(statusJson, Status.class));
    }

    public StatusCodeException(final Status status) {
        super(status.toString());
        mStatusCode = status.getHttpStatus();
    }

    public int getStatusCode() {
        return mStatusCode;
    }
}
