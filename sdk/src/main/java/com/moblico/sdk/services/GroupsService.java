package com.moblico.sdk.services;

import android.net.Uri;
import androidx.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.Group;
import com.moblico.sdk.entities.Location;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class GroupsService {

    private GroupsService() {
    }

    public static void getGroups(final Callback<List<Group>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("groups", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<Group>>() {}.getType();
                        List<Group> groups = Moblico.getGson().fromJson(result, collectionType);
                        Iterator<Group> it = groups.iterator();
                        while (it.hasNext()) {
                            if (!it.next().isRegisterable()) {
                                it.remove();
                            }
                        }
                        callback.onSuccess(groups);
                    }
                });
            }
        });
    }

    public static void joinGroup(final long id, final @Nullable Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.put("users/" + Uri.encode(Moblico.getUsername()) + "/groups/" + id, null, new ErrorForwardingCallback<String>(callback) {
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

    public static void leaveGroup(final long id, final @Nullable Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("groupId", Long.toString(id));
                HttpRequest.delete("users/" + Uri.encode(Moblico.getUsername()) + "/groups", params, new ErrorForwardingCallback<String>(callback) {
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
