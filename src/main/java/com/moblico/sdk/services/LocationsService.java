package com.moblico.sdk.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Location;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public final class LocationsService {

    LocationsService() {
    }

    public void findLocations(final Callback<List<Location>> callback) {
        Moblico.getAuthenticationService().authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                // TODO: pass in current location, if known?
                HttpRequest.get("locations", null, new Callback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        GsonBuilder builder = new GsonBuilder();
                        // Register an adapter to manage the date types as long values
                        final GsonBuilder gsonBuilder = builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                return new Date(json.getAsJsonPrimitive().getAsLong());
                            }
                        });
                        final Gson gson = builder.create();
                        Type collectionType = new TypeToken<List<Location>>() { }.getType();
                        List<Location> locations = gson.fromJson(result, collectionType);
                        callback.onSuccess(locations);
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        callback.onFailure(caught);
                    }
                });
            }
        });

    }
}
