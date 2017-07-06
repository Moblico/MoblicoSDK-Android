package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Affinity;
import com.moblico.sdk.entities.Location;
import com.moblico.sdk.entities.Points;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PointsService {

    private PointsService() {
    }

    public static void getPoints(final Callback<Points> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                final Map<String, String> params = new HashMap<>();
                params.put("pointTypeName", "Interactive");
                HttpRequest.get("users/" + Moblico.getUsername() + "/points", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Points>>() {}.getType();
                        List<Points> points = Moblico.getGson().fromJson(result, collectionType);
                        if (points.isEmpty()) {
                            callback.onFailure(new RuntimeException("Empty points list"));
                        }
                        callback.onSuccess(points.get(0));
                    }
                });
            }
        });
    }
}
