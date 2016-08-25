package com.moblico.sdk.services;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Media;

import java.lang.reflect.Type;
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

        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("media", params, new ErrorForwardingCallback<String>(callback) {

                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Media>>() { }.getType();
                        List<Media> media = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(media);
                    }
                });
            }
        } );
    }

    public static void getFromParent(final long parentId, final Callback<List<Media>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("media/" + parentId + "/media", null, new ErrorForwardingCallback<String>(callback) {

                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Media>>() { }.getType();
                        List<Media> media = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(media);
                    }
                });
            }
        } );
    }
}
