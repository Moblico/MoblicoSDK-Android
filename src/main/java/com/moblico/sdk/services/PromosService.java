package com.moblico.sdk.services;


import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Promos;

import java.lang.reflect.Type;
import java.util.List;

public class PromosService {
    private PromosService() {
    }

    public static void getPromos(final Callback<List<Promos>> callback){
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("promos", null, new ErrorForwardingCallback<String>(callback) {
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
