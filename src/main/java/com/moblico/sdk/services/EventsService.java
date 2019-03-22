package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Event;
import com.moblico.sdk.entities.Location;

import java.lang.reflect.Type;
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
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("events", null, new ErrorForwardingCallback<String>(callback) {
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
