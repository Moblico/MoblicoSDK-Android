package com.moblico.sdk.services;

public interface Callback<T> {
    void onSuccess(T result);

    void onFailure(Throwable caught);
}
