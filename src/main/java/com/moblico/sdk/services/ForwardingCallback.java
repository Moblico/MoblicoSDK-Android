package com.moblico.sdk.services;

public class ForwardingCallback<T> implements Callback<T> {
    private final Callback<T> mCallback;

    public ForwardingCallback(final Callback<T> callback) {
        mCallback = callback;
    }

    @Override
    public void onSuccess(T result) {
        mCallback.onSuccess(result);
    }

    @Override
    public void onFailure(Throwable caught) {
        mCallback.onFailure(caught);
    }
}
