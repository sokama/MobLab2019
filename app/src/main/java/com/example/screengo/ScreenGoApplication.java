package com.example.screengo;

import com.orm.SugarApp;

public class ScreenGoApplication extends SugarApp {
    public static ScreenGoApplicationComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();
        injector = DaggerScreenGoApplicationComponent.builder().build();
    }
}
