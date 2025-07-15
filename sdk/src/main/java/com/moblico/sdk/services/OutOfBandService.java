package com.moblico.sdk.services;

import android.net.Uri;
import java.util.Map;

public class OutOfBandService {
    public static void read(String endPoint, Map<String, String> params, Callback<String> callback) {
        exec(endPoint, params, null, callback, false);
    }

    public static void write(String endPoint, Map<String, String> params, String body, Callback<String> callback) {
        exec(endPoint, params, body, callback, true);
    }

    private static void exec(String endPoint, Map<String, String> params, String body, Callback<String> callback, boolean isPost) {
        AuthenticationService.authenticate(new ErrorForwardingCallback<Void>(callback) {

            @Override
            public void onSuccess(Void unused) {
                ErrorForwardingCallback<String> forwardingCallback = new ErrorForwardingCallback<String>(callback) {

                    @Override
                    public void onSuccess(String result) {
                        if (callback != null) {
                            callback.onSuccess(result);
                        }
                    }
                };

                String path = "outofband/" + Uri.encode(endPoint);
                if (isPost) {
                    HttpRequest.post(path, params, body, forwardingCallback);
                } else {
                    HttpRequest.get(path, params, forwardingCallback);
                }
            }
        });
    }
}
