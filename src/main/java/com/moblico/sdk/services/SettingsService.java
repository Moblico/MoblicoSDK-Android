package com.moblico.sdk.services;

public final class SettingsService {

    private SettingsService() {
    }

    public static void loadSettings(final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("settings", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Moblico.getSettings().parseNewSettings(result);
                        callback.onSuccess(null);
                    }
                });
            }
        });

    }
}
