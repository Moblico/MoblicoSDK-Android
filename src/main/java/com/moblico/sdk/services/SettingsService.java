package com.moblico.sdk.services;

public final class SettingsService {

    SettingsService() {
    }

    public void loadSettings(final Callback<Void> callback) {
        Moblico.getAuthenticationService().authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("settings", null, new Callback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Moblico.getSettings().parseNewSettings(result);
                        callback.onSuccess(null);
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        callback.onFailure(caught);
                    }
                });
            }
        });

    }
}
