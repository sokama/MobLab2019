package com.example.screengo.ui;

import com.example.screengo.interactor.PlacesInteractor;

import javax.inject.Inject;

public class MainPresenter extends Presenter<MainScreen> {
    public PlacesInteractor placesInteractor;

    @Inject
    public MainPresenter(PlacesInteractor placesInteractor) {
        this.placesInteractor = placesInteractor;
    }

    @Override
    public void attachScreen(MainScreen screen) {
        super.attachScreen(screen);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }

    public void refreshWeather() {
        boolean isSunny = false; // TODO: get weather from public API
        screen.showWeather(isSunny);
    }

    public void refreshPlaces() {
        placesInteractor.getPlaces();
        // TODO: get place list and pass it to the screen as a function parameter
        screen.showPlaces();
    }
}
