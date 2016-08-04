package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Event;
import com.moblico.sdk.entities.Location;
import com.moblico.sdk.entities.Reward;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventsService {

    private EventsService() {
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

}
