package com.moblico.sdk.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Location;
import com.moblico.sdk.services.exceptions.NoLocationException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class LocationsService {
    public enum Parameter {
        TYPE("type"),
        ZIPCODE("zipcode"),
        LATITUDE("latitude"),
        LONGITUDE("longitude"),
        RADIUS("radius"),
        PAGE("page"),
        MERCHANT_ID("merchantId"),
        CHECKIN_ENABLED("checkInEnabled"),
        NOTIFICATION_ENABLED("notificationEnabled");

        private final String name;

        Parameter(final String name) {
            this.name = name;
        }
    }

    private LocationsService() {
    }

    /**
     * Find locations.  To return a distance to the user, and sort the locations by distance,
     * include the context parameter.  If distance isn't important, context can be null.
     */
    public static void findLocations(final Context context, final boolean currentLocationRequired,
                                     final @NonNull Map<Parameter, String> parameters, final Callback<List<Location>> callback) {
        if (context == null && currentLocationRequired) {
            callback.onFailure(new NoLocationException());
            return;
        }

        if (context != null) {
            android.location.Location location = findLocation(context);
            if (location == null && currentLocationRequired) {
                callback.onFailure(new NoLocationException());
                return;
            }
            if (location != null) {
                parameters.put(Parameter.LATITUDE, Double.toString(location.getLatitude()));
                parameters.put(Parameter.LONGITUDE, Double.toString(location.getLongitude()));
            }
        }
        findLocations(parameters, callback);
    }

    /**
     * Find locations.  To return a distance to the user, and sort the locations by distance,
     * include the context parameter.  If distance isn't important, context can be null.
     */
    public static void findLocations(final @NonNull Context context, final Callback<List<Location>> callback) {
        findLocations(context, true, new HashMap<Parameter, String>(), callback);
    }

    public static void findLocationsByZip(final @NonNull String zipcode, final Callback<List<Location>> callback) {
        HashMap<Parameter, String> parameters = new HashMap<>();
        parameters.put(Parameter.ZIPCODE, zipcode);
        findLocations(parameters, callback);
    }

    public static void findLocations(final Map<Parameter, String> parameters, final Callback<List<Location>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HashMap<String, String> params = null;
                if (parameters != null) {
                    params = new HashMap<>();
                    for (Map.Entry<Parameter,String> entry : parameters.entrySet()) {
                        params.put(entry.getKey().name, entry.getValue());
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

    public static void findLocationsForEvent(final long eventId, final Callback<List<Location>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("events/" + eventId + "/locations", null, new ErrorForwardingCallback<String>(callback) {
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

    public static void checkin(final Location location, final Context context, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("locationId", Long.toString(location.getId()));
                if (location.getBeaconIdentifier() != null && !location.getBeaconIdentifier().isEmpty()) {
                    params.put("beaconIdentifier", location.getBeaconIdentifier());
                }
                if (context != null) {
                    android.location.Location l = findLocation(context);
                    if (l != null) {
                        params.put("latitude", Double.toString(l.getLatitude()));
                        params.put("longitude", Double.toString(l.getLongitude()));
                        params.put("locationAccuracy", Float.toString(l.getAccuracy()));
                    }
                }
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

    @SuppressLint("MissingPermission")
    protected static android.location.Location findLocation(Context context) {
        if (!haveLocationPermission(context)) {
            // We aren't going to get the location here, so just return null.
            // Ideally we'd get notified that the user has accepted the request, but this is unfortunately
            // done through an activity callback.  We don't have access to any activity callbacks from
            // here, so we will just try later if needed.
            return null;
        }
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

    public static boolean haveLocationPermission(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (!(context instanceof Activity)) {
                // There isn't a good way to request a permission from outside of an activity.  We
                // could show a notification to the user and then request the permission when they
                // open the notification, but that is ugly at best.  For now, let's just assume they
                // didn't give us the permission.
                return false;
            }
            final Activity activity = (Activity)context;
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ApplicationInfo applicationInfo = context.getApplicationInfo();
                int stringId = applicationInfo.labelRes;
                String appName = stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
                new AlertDialog.Builder(activity)
                        .setTitle("Location Permission Needed")
                        .setMessage(appName + " uses your location to find relevant points of interest near you!")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestLocationPermissions(activity);
                            }
                        })
                        .show();
            } else {
                requestLocationPermissions(activity);
            }
            return false;
        }
        return true;
    }

    private static void requestLocationPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                0);
    }
}
