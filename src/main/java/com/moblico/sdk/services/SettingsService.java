package com.moblico.sdk.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.AuthenticationToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class SettingsService {

    SettingsService() {
    }

    public void loadSettings(final Callback<Void> callback) {
        Moblico.getAuthenticationService().authenticate(new ForwardingCallback<Void>(callback) {
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
