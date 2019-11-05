package com.moblico.sdk.services;

import android.net.Uri;

import com.google.gson.reflect.TypeToken;
import com.moblico.sdk.entities.InboxMessage;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InboxMessagesService {

    private InboxMessagesService() {
    }

    public static void getInboxMessages(final Callback<List<InboxMessage>> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("users/" + Uri.encode(Moblico.getUsername()) + "/inbox", null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        Type collectionType = new TypeToken<List<InboxMessage>>() {}.getType();
                        List<InboxMessage> inboxMessages = Moblico.getGson().fromJson(result, collectionType);
                        callback.onSuccess(inboxMessages);
                    }
                });
            }
        });
    }

    public static void markMessageRead(final long messageId, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("status", "OPENED");
                HttpRequest.put("message/" + messageId, params, new ErrorForwardingCallback<String>(callback) {
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

    public static void deleteMessage(final long messageId, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.delete("users/" + Uri.encode(Moblico.getUsername()) + "/inbox/" + messageId, null, new ErrorForwardingCallback<String>(callback) {
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
