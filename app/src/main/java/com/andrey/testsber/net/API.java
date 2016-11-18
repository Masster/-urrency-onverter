package com.andrey.testsber.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.andrey.testsber.MainApp;
import com.andrey.testsber.utils.InputStreamUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Andrey Antonenko on 18.11.2016.
 */

public class API {
    public static final String BASE_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
    public static final int DEFAULT_CONNECTION_TIMEOUT = 15000;
    public static final int DEFAULT_READ_TIMEOUT = 60000;
    public static final String GET = "GET";

    private static Executor executor = Executors.newSingleThreadExecutor();
    private static Handler handler;

    private static boolean isConnected() {
        Context context = MainApp.getInstance();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            return false;
        }
        return ni.isConnected();
    }

    private static Response sendRequest(String url) {

        if (!isConnected()) {
            Log.d(API.class.getCanonicalName(), "Cancelling request: no connection.");
            return new Response(ServerError.NO_CONNECTION);
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(GET);
            connection.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
            connection.setReadTimeout(DEFAULT_READ_TIMEOUT);
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.setUseCaches(false);

            int responseCode = connection.getResponseCode();

            String response = "";
            if (responseCode == 200) {
                response = InputStreamUtils.toString(connection.getInputStream());
            } else {
                response = InputStreamUtils.toString(connection.getErrorStream());
            }

            connection.disconnect();
            return new Response(responseCode, response);
        } catch (Exception e) {
            return new Response(ServerError.UNKNOWN_ERROR);
        }
    }

    public static void sendRequestAsync(final String url, final OnRequestSentListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (handler == null) {
                    handler = new Handler(Looper.getMainLooper());
                }
                final Response response = API.sendRequest(url);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.requestSent(response);
                        }
                    }
                });
            }
        });

    }
}
