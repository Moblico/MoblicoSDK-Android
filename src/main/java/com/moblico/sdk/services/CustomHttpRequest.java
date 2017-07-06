package com.moblico.sdk.services;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/** This is identical to HttpRequest, but able to send to non-Moblico URLs. */
public class CustomHttpRequest extends AsyncTask<URL, Void, String> {

    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    private static final String TAG = CustomHttpRequest.class.getName();

    static public void get(final URL url, final Callback<String> callback) {
        new CustomHttpRequest(url, "GET", null, null, callback);
    }

    /** Generate a POST request on the specified URL.  An optional requestBody should be null if not
     * needed.  The post body is assumed to be json format. */
    static public void post(final URL url, final String requestBody, final Callback<String> callback) {
        post(url, requestBody, CONTENT_TYPE_JSON, callback);
    }

    static public void post(final URL url, final String requestBody, final String requestContentType, final Callback<String> callback) {
        new CustomHttpRequest(url, "POST", requestBody, requestContentType, callback);
    }

    /** Generate a PUT request on the specified URL.  An optional requestBody should be null if not
     * needed. */
    static public void put(final URL url, final String requestBody, final Callback<String> callback) {
        new CustomHttpRequest(url, "PUT", requestBody, CONTENT_TYPE_JSON, callback);
    }

    /** Generate a DELETE request on the specified URL.  An optional requestBody should be null if not
     * needed. */
    static public void delete(final URL url, final String requestBody, final Callback<String> callback) {
        new CustomHttpRequest(url, "DELETE", requestBody, CONTENT_TYPE_JSON, callback);
    }

    private final Callback<String> mCallback;
    private final String mRequestMethod;
    private final String mRequestBody;
    private final String mContentType;
    private Throwable mThrowable;

    private CustomHttpRequest(final URL url, final String requestMethod, final String requestBody, final String contentType, final Callback<String> callback) {
        mCallback = callback;
        mRequestMethod = requestMethod;
        mRequestBody = requestBody;
        mContentType = contentType;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // On newer devices, support sending http requests in parallel using a thread pool.
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        } else {
            execute(url);
        }
    }

    @NonNull
    private String fromStream(final InputStream stream) throws IOException {
        if (stream == null) {
            return "Unable to open stream.";
        }
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
            if (mRequestBody != null) {
                Log.i(TAG, "With body: " + mRequestBody);
            }
        }
        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            // Set the timeouts to 2 minutes - we've seen one case where auto checkin requests were
            // queued up for nearly an hour, and wonder if the connections weren't timing out properly
            // on a poor network connection.
            urlConnection.setConnectTimeout(120 * 1000);
            urlConnection.setReadTimeout(120 * 1000);
            urlConnection.setRequestMethod(mRequestMethod);
            urlConnection.setRequestProperty("Accept", CONTENT_TYPE_JSON);

            if (mRequestBody != null) {
                urlConnection.setRequestProperty("Content-Type", mContentType);
                urlConnection.setDoOutput(true);
                byte[] utf8text = mRequestBody.getBytes("UTF-8");
                urlConnection.setFixedLengthStreamingMode(utf8text.length);
                OutputStream os = urlConnection.getOutputStream();
                os.write(utf8text);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            mThrowable = e;
            return null;
        }
        try {
            urlConnection.connect();
            final int responseCode = urlConnection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
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
