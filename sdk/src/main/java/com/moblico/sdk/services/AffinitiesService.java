package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Affinity;
import com.moblico.sdk.entities.Location;
import com.moblico.sdk.entities.Reward;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AffinitiesService {

    private AffinitiesService() {
    }

    public static void getAffinities(final Callback<List<Affinity>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("affinities", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Affinity>>() {}.getType();
                        List<Affinity> rewards = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(rewards);
                    }
                });
            }
        });
    }

    public static void getAffinitiesAtLocation(final Location location, final Callback<List<Affinity>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("locations/" + location.getId() + "/affinities", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Affinity>>() {}.getType();
                        List<Affinity> rewards = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(rewards);
                    }
                });
            }
        });
    }

    public static void scanCode(final String qrCodeId, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("qrCodeId", qrCodeId);
                HttpRequest.put("users/" + Moblico.getUsername() + "/points/scan", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }
}
