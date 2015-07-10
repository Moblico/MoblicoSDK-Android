package com.moblico.sdk.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Location;
import com.moblico.sdk.entities.Media;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaService {

    private MediaService() {
    }

    public static void findAll(final String category, final String mediaType, final String mediaTypeCategory, final Callback<List<Media>> callback) {
        final Map<String, String> params = new HashMap<String, String>();
        if(category != null) params.put("category", category);
        if(mediaType != null) params.put("mediaType", mediaType);
        if(mediaTypeCategory != null) params.put("mediaTypeCategory", mediaTypeCategory);

        Moblico.getAuthenticationService().authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("media", params, new Callback<String>() {

                    @Override
                    public void onSuccess(String result) {
                        GsonBuilder builder = new GsonBuilder();
                        // Register an adapter to manage the date types as long values
                        // TODO: centralize this.
                        final GsonBuilder gsonBuilder = builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                return new Date(json.getAsJsonPrimitive().getAsLong());
                            }
                        });
                        final Gson gson = builder.create();
                        Type collectionType = new TypeToken<List<Media>>() { }.getType();
                        List<Media> locations = gson.fromJson(result, collectionType);
                        callback.onSuccess(locations);
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        callback.onFailure(caught);
                    }
                });
            }
        } );
    }
}
