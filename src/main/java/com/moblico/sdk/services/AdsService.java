package com.moblico.sdk.services;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Ad;
import com.moblico.sdk.entities.Deal;
import com.moblico.sdk.entities.Location;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AdsService {

    private AdsService() {
    }

    public static void getAd(@NonNull final String adContext, final Callback<Ad> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("context", adContext);
                HttpRequest.get("ad", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Ad ad = Moblico.getGson().fromJson(result, Ad.class);
                        callback.onSuccess(ad);
                    }
                });
            }
        });
    }
}
