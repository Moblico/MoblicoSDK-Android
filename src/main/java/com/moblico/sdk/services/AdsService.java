package com.moblico.sdk.services;

import androidx.annotation.NonNull;

import com.moblico.sdk.entities.Ad;

import java.util.HashMap;
import java.util.Map;

public final class AdsService {

    public enum Type {
        AD_BANNER,
        AD_PROMO,
        AD_SPONSOR
    }

    private AdsService() {
    }

    public static void getAd(@NonNull final String adContext, @NonNull final Type type, final Callback<Ad> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("context", adContext);
                params.put("type", type.toString());
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
