package com.example.screengo.ui;

import javax.inject.Inject;

public class MainPresenter extends Presenter<MainScreen> {
    @Inject
    public MainPresenter() {}

    @Override
    public void attachScreen(MainScreen screen) {
        super.attachScreen(screen);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }

    public void updateWeather() {
        boolean isSunny = false; // TODO: get weather from public API
        screen.showWeather(isSunny);
    }
}
