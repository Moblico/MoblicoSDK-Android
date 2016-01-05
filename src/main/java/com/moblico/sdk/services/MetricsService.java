package com.moblico.sdk.services;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class MetricsService {

    public enum Type {
        APPLICATION_START("Application_Start"),
        APPLICATION_STOP("Application_Stop"),
        IN_BACKGROUND("In_Background"),
        OUT_BACKGROUND("Out_Background"),
        ENTER_PAGE("Enter_Page"),
        EXIT_PAGE("Exit_Page"),
        AD_CLICK("Ad_Click"),
        TRACKING("Tracking"),
        CUSTOM("Custom"),
        VIEW_DEAL("View_Deal"),
        VIEW_REWARD("View_Reward"),
        VIEW_LOCATION("View_Location"),
        VIEW_EVENT("View_Event"),
        VIEW_MEDIA("View_Media"),
        SHARE_APP("Share_App"),
        SHARE_DEAL("Share_Deal"),
        SHARE_REWARD("Share_Reward"),
        SHARE_LOCATION("Share_Location"),
        ENTER_GEO_REGION("Enter_Geo_Region"),
        EXIT_GEO_REGION("Exit_Geo_Region"),
        ENTER_BEACON_REGION("Enter_Beacon_Region"),
        EXIT_BEACON_REGION("Exit_Beacon_Region"),
        GPS_CHANGED("Change_GPS");

        private final String text;
        private Type(String text) {
            this.text = text;
        }
    }

    private MetricsService() {
    }

    public static void send(@NonNull final Type type, @Nullable final String text,
                            @Nullable final Context context, @Nullable final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                // TODO: pass in current location, if known
                Map<String, String> params = new HashMap<>();
                params.put("type", type.text);
                params.put("timestamp", Long.toString(System.currentTimeMillis()));
                if (text != null) {
                    params.put("text", text);
                }
                if (context != null) {
                    Location loc = LocationsService.findLocation(context);
                    if (loc != null) {
                        params.put("latitude", Double.toString(loc.getLatitude()));
                        params.put("longitude", Double.toString(loc.getLongitude()));
                    }
                }
                HttpRequest.post("metrics", params, new ErrorForwardingCallback<String>(callback) {
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
}
