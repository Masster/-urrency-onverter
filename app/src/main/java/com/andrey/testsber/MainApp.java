package com.andrey.testsber;

import android.app.Application;

/**
 * Created by Andrey Antonenko on 18.11.2016.
 */

public class MainApp extends Application {
    private static MainApp instance;

    public static MainApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
