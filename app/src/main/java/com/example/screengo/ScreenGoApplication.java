package com.example.screengo;

import android.app.Application;

public class ScreenGoApplication extends Application {
    public static ScreenGoApplicationComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();
        injector = DaggerScreenGoApplicationComponent.builder().build();
    }
}
