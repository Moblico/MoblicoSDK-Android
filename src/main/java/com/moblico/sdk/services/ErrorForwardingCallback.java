package com.moblico.sdk.services;

public abstract class ErrorForwardingCallback<T> implements Callback<T> {
    private final Callback mCallback;

    public ErrorForwardingCallback(final Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onFailure(Throwable caught) {
        if (mCallback != null) {
            mCallback.onFailure(caught);
        }
    }
}
