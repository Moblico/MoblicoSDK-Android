package com.moblico.sdk.services;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest extends AsyncTask<URL, Void, String> {
    private static final String TAG = HttpRequest.class.getName();

    static public void get(final String path, final Map<String, String> params, final Callback<String> callback) {
        try {
            final URL url = Moblico.buildUrl(path, params);
            new HttpRequest(url, "GET", callback);
        } catch (MalformedURLException e) {
            callback.onFailure(e);
        }
    }

    /** Run a get request on the specified URL.  The same as {@link #get(String, Map, Callback)} but
     * able to target a custom URL.
     */
    static public void get(final String urlStr, final Callback<String> callback) {
        try {
            final URL url = new URL(urlStr);
            new HttpRequest(url, "GET", callback);
        } catch (MalformedURLException e) {
            callback.onFailure(e);
        }
    }

    static public void post(final String path, final Map<String, String> params, final Callback<String> callback) {
        try {
            final URL url = Moblico.buildUrl(path, params);
            new HttpRequest(url, "POST", callback);
        } catch (MalformedURLException e) {
            callback.onFailure(e);
        }
    }

    static public void put(final String path, final Map<String, String> params, final Callback<String> callback) {
        try {
            final URL url = Moblico.buildUrl(path, params);
            new HttpRequest(url, "PUT", callback);
        } catch (MalformedURLException e) {
            callback.onFailure(e);
        }
    }

    static public void delete(final String path, final Map<String, String> params, final Callback<String> callback) {
        try {
            final URL url = Moblico.buildUrl(path, params);
            new HttpRequest(url, "DELETE", callback);
        } catch (MalformedURLException e) {
            callback.onFailure(e);
        }
    }

    private final Callback<String> mCallback;
    private final String mRequestMethod;
    private Throwable mThrowable;

    private HttpRequest(final URL url, final String requestMethod, final Callback<String> callback) {
        mCallback = callback;
        mRequestMethod = requestMethod;
        execute(url);
    }

    @NonNull
    private String fromStream(final InputStream stream) throws IOException {
        final char[] buffer = new char[1024];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(stream, "UTF-8");
        while (true) {
            int size = in.read(buffer, 0, buffer.length);
            // TODO: more support for cancelling requests.
            if(size < 0 || isCancelled()) {
                break;
            }
            out.append(buffer, 0, size);
        }
        return out.toString();
    }

    @Override
    protected String doInBackground(URL... params) {
        URL url = params[0];
        if(Moblico.isLogging()) {
            Log.i(TAG, "Sending " + mRequestMethod + " to " + url.toString());
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod(mRequestMethod);
            urlConnection.setRequestProperty("Accept", "application/json");
        } catch (IOException e) {
            mThrowable = e;
            return null;
        }
        try {
            final int responseCode = urlConnection.getResponseCode();
            if (responseCode != 200) {
                if (Moblico.isLogging()) {
                    Log.e(TAG, "Got failed response code: " + responseCode);
                }
                InputStream stream = urlConnection.getErrorStream();
                String string = fromStream(stream);
                if (Moblico.isLogging()) {
                    Log.e(TAG, "Failure message: " + string);
                }
                try {
                    mThrowable = new StatusCodeException(string);
                } catch (Exception e) {
                    mThrowable = new StatusCodeException(responseCode);
                }
                return null;
            }
            InputStream stream = urlConnection.getInputStream();
            String string = fromStream(stream);
            if (Moblico.isLogging()) {
                Log.i(TAG, "Got response: " + string);
            }
            return string;
        } catch (IOException e) {
            mThrowable = e;
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            mCallback.onSuccess(result);
        } else if (mThrowable != null) {
            mCallback.onFailure(mThrowable);
        } else {
            mCallback.onFailure(new RuntimeException("Connection finished with no result or exception."));
        }
    }
}
