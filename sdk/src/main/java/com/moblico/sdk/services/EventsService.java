package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Event;
import com.moblico.sdk.entities.Location;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

public final class EventsService {

    private EventsService() {
    }

    public static void getEvent(final long eventId, final Callback<Event> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("events/" + eventId, null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Event event = Moblico.getGson().fromJson(result, Event.class);
                        callback.onSuccess(event);
                    }
                });
            }
        });
    }

    public static void getEvents(final Callback<List<Event>> callback) {
        getEvents((String) null, callback);
    }

    public static void getEvents(final String type, final Callback<List<Event>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HashMap<String, String> params = null;
                if (type != null && type.length() > 0) {
                    params = new HashMap<>();
                    params.put("type", type);
                }
                HttpRequest.get("events", params, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Event>>() {}.getType();
                        List<Event> events = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(events);
                    }
                });
            }
        });
    }

    public static void getEvents(final Location location, final Callback<List<Event>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("locations/" + location.getId() + "/events", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Event>>() {}.getType();
                        List<Event> events = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(events);
                    }
                });
            }
        });
    }

}
