package com.moblico.sdk.services;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class HttpRequest extends AsyncTask<URL, Void, String> {

    static void get(final String path, final Callback<String> callback) throws MalformedURLException {
        URL url = new URL("https://moblico.net/services/v4/authenticate?apikey=e44ed355-9d91-4db2-9aba-7ffd52d1dac2&platformName=ANDROID");
        new HttpRequest(url, callback);
    }

    private final Callback<String> mCallback;

    private HttpRequest(final URL url, final Callback<String> callback) {
        mCallback = callback;
        execute(url);
    }

    @NonNull
    private String fromStream(final InputStream stream) throws IOException {
        final char[] buffer = new char[1024];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(stream, "UTF-8");
        while (true) {
            int size = in.read(buffer, 0, buffer.length);
            if(size < 0) {
                break;
            }
            out.append(buffer, 0, size);
        }
        return out.toString();
    }

    @Override
    protected String doInBackground(URL... params) {
        URL url = params[0];
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // check response code.
            InputStream stream = urlConnection.getInputStream();
            String string = fromStream(stream);
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        mCallback.onSuccess(result);
    }
}
