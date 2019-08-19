package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Credential;

import java.lang.reflect.Type;
import java.util.List;

public final class CredentialsService {

    private CredentialsService() {
    }

    public static void getCredentials(final Callback<List<Credential>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("credentials", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Credential>>() {}.getType();
                        List<Credential> credentials = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(credentials);
                    }
                });
            }
        });
    }
}
