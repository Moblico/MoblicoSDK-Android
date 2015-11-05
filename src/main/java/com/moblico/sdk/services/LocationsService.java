package com.moblico.sdk.services;

import android.content.Context;
import android.location.LocationManager;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Location;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LocationsService {

    private LocationsService() {
    }

    /**
     * Find locations.  To return a distance to the user, and sort the locations by distance,
     * include the context parameter.  If distance isn't important, context can be null.
     */
    public static void findLocations(final Context context, final Callback<List<Location>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                if (context != null) {
                    android.location.Location location = findLocation(context);
                    if (location != null) {
                        params.put("latitude", Double.toString(location.getLatitude()));
                        params.put("longitude", Double.toString(location.getLongitude()));
                    }
                }
                HttpRequest.get("locations", params, new ErrorForwardingCallback<String>(callback) {
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

    public static void getLocation(final long locationId, final Callback<Location> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("locations/" + locationId, null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Location location = Moblico.getGson().fromJson(result, Location.class);
                        callback.onSuccess(location);
                    }
                });
            }
        });
    }

    public static void checkin(final Location location, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("locationId", Long.toString(location.getId()));
                HttpRequest.post("checkIn", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        if (callback != null) {
                            callback.onSuccess(null);
                        }
                    }
                });
            }
        });
    }

    private static android.location.Location findLocation(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        android.location.Location gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        android.location.Location networkLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (gpsLocation == null) {
            return networkLocation;
        } else if (networkLocation == null) {
            return gpsLocation;
        }
        if (gpsLocation.getTime() < networkLocation.getTime()) {
            return networkLocation;
        } else {
            return gpsLocation;
        }
    }
}
