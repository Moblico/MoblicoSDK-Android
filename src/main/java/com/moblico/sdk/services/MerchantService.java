package com.moblico.sdk.services;

import android.content.Context;
import androidx.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Location;
import com.moblico.sdk.entities.Merchant;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MerchantService {

    private MerchantService() {
    }

    public static void getMerchant(final long id, final Callback<Merchant> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("merchants/" + id, null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Merchant merchant = Moblico.getGson().fromJson(result, Merchant.class);
                        callback.onSuccess(merchant);
                    }
                });
            }
        } );
    }

    public static void findLocations(final Context context, final Callback<List<Location>> callback) {
        if (context != null) {
            android.location.Location location = LocationsService.findLocation(context);
            if (location != null) {
                findLocations(callback, "latitude", Double.toString(location.getLatitude()),
                        "longitude", Double.toString(location.getLongitude()));
                return;
            }
        }
        findLocations(callback);
    }

    public static void findLocationsByZip(final @NonNull String zipcode, final Callback<List<Location>> callback) {
        findLocations(callback, "zipcode", zipcode);
    }

    public static void findLocations(final Callback<List<Location>> callback, final String... parameters) {
        if ((parameters.length & 1) == 1) {
            // The parameter length is odd, we must have an even number for key:value pairs!
            throw new IllegalArgumentException("An even number of parameters is required");
        }
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                for (int i = 0; i < parameters.length; i+=2) {
                    params.put(parameters[i], parameters[i+1]);
                }
                HttpRequest.get("merchants/" + Moblico.getUser().getMerchantId() + "/locations", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Location>>() {}.getType();
                        List<Location> locations = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(locations);
                    }
                });
            }
        });
    }
}
