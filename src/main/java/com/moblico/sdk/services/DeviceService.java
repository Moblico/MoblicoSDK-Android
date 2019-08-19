package com.moblico.sdk.services;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;


import java.util.HashMap;
import java.util.Map;

public class DeviceService {

    public static final String EXTRA_MESSAGE_ID = "com.moblico.private.intent.EXTRA_MESSAGE_ID";
    public static final String EXTRA_NOTIFICATION_TITLE = "com.moblico.intent.EXTRA_NOTIFICATION_TITLE";
    public static final String EXTRA_NOTIFICATION_BODY = "com.moblico.intent.EXTRA_NOTIFICATION_BODY";
    public static final String EXTRA_NOTIFICATION_DEAL_ID = "com.moblico.intent.EXTRA_NOTIFICATION_DEAL_ID";

    public enum MessageStatus {
        RECEIVED, OPENED
    }

    private static class NotificationMessage {
        public final String notificationMessage;
        // This is just used for gson, no need for a constructor.
        private NotificationMessage() {
            notificationMessage = null;
        }
    }

    private DeviceService() {
    }

    public static void receivedMessage(final long messageId, final @NonNull MessageStatus status, final Callback<Void> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("status", status.toString());
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

    public static void getNotification(final long notificationId, final @NonNull Callback<String> callback) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.get("notifications/" + notificationId, null, new ErrorForwardingCallback<String>(callback) {
                    @Override
                    public void onSuccess(String result) {
                        NotificationMessage notification = Moblico.getGson().fromJson(result, NotificationMessage.class);
                        callback.onSuccess(notification.notificationMessage);
                    }
                });
            }
        });
    }



    /**
     * Unregister the device from Moblico so they will no longer receive push notifications.
     */
    public static void unregisterDevice(final @NonNull Context context) {
//        Intent i = new Intent(context, RegistrationService.class);
//        i.putExtra(RegistrationService.EXTRA_UNREGISTER, true);
//        context.startService(i);
        AuthenticationService.authenticate(new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                HttpRequest.delete("users/" + Uri.encode(Moblico.getUsername()) + "/device", null, new Callback<String>() {
                    @Override
                    public void onSuccess(String result) {
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                    }
                });
            }

            @Override
            public void onFailure(Throwable caught) {
            }
        });
    }

    /**
     * Register a user's device with the given device ID.  This is typically used as an internal
     * API call, and most developers will want to call {@link DeviceService# registerDevice(Context)}
     * instead.
     */
    public static void registerDevice(final @NonNull String deviceId, final Callback<Void> callback) {
        if (Moblico.getUsername() == null) {
            if (callback != null) {
                callback.onFailure(new IllegalStateException("Must log with a valid user before registering device"));
            }
            return;
        }
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {
            @Override
            public void onSuccess(Void result) {
                Map<String, String> params = new HashMap<>();
                params.put("deviceId", deviceId);
                HttpRequest.put("users/" + Uri.encode(Moblico.getUsername()) + "/device", params, new ErrorForwardingCallback<String>(callback) {
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
