package com.moblico.sdk.services;


import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Promos;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromosService {
    private PromosService() {
    }

    public static void getPromos(@NonNull final String adContext, final Callback<List<Promos>> callback){
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("context", adContext);
                HttpRequest.get("promos", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Promos>>() {}.getType();
                        List<Promos> promos = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(promos);
                    }
                });
            }
        });
    }
}
