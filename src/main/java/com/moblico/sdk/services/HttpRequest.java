package com.moblico.sdk.services;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class HttpRequest {
    static public void get(final String path, final Map<String, String> params, final Callback<String> callback) {
        try {
            final URL url = Moblico.buildUrl(path, params);
            CustomHttpRequest.get(url, callback);
        } catch (MalformedURLException e) {
            callback.onFailure(e);
        }
    }

    static public void post(final String path, final Map<String, String> params, final Callback<String> callback) {
        post(path, params, null, callback);
    }

    static public void post(final String path, final Map<String, String> params, String body, final Callback<String> callback) {
        try {
            final URL url = Moblico.buildUrl(path, params);
            CustomHttpRequest.post(url, body, callback);
        } catch (MalformedURLException e) {
            callback.onFailure(e);
        }
    }

    static public void put(final String path, final Map<String, String> params, final Callback<String> callback) {
        try {
            final URL url = Moblico.buildUrl(path, params);
            CustomHttpRequest.put(url, null, callback);
        } catch (MalformedURLException e) {
            callback.onFailure(e);
        }
    }

    static public void delete(final String path, final Map<String, String> params, final Callback<String> callback) {
        try {
            final URL url = Moblico.buildUrl(path, params);
            CustomHttpRequest.delete(url, null, callback);
        } catch (MalformedURLException e) {
            callback.onFailure(e);
        }
    }
}
