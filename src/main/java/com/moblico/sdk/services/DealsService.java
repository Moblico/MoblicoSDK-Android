package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Deal;
import com.moblico.sdk.entities.Location;

import java.lang.reflect.Type;
import java.util.List;

public final class DealsService {

    private DealsService() {
    }

    public static void getDeals(final Callback<List<Deal>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("deals", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Deal>>() {}.getType();
                        List<Deal> deals = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(deals);
                    }
                });
            }
        });
    }
}
