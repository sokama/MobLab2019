package com.example.screengo.ui;

import com.example.screengo.interactor.LocationInteractor;
import com.example.screengo.interactor.PlacesInteractor;

import java.util.concurrent.Executor;

import javax.inject.Inject;

public class MainPresenter extends Presenter<MainScreen> {
    public PlacesInteractor placesInteractor;
    public LocationInteractor locationInteractor;
    public Executor networkExecutor;

    @Inject
    public MainPresenter(PlacesInteractor placesInteractor,
                         LocationInteractor locationInteractor,
                         Executor networkExecutor) {
        this.placesInteractor = placesInteractor;
        this.locationInteractor = locationInteractor;
        this.networkExecutor = networkExecutor;
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
        // Network calls can't be on main thread
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // Get weather info
                String cityName = "Budapest"; // TODO: get city name from location interactor
                int locationId = locationInteractor.getLocationId(cityName); // TODO: this should be stored on startup
                String weatherState = locationInteractor.getWeatherState(locationId);
                boolean isSunny = locationInteractor.isWeatherSunny(weatherState);

                // Refresh screen
                screen.showWeather(String.valueOf(locationId), isSunny); // todo return state instead of id
            }
        });

    }

    public void refreshPlaces() {
        placesInteractor.getPlaces();
        // TODO: get place list and pass it to the screen as a function parameter
        screen.showPlaces();
    }

    public void deletePlace() {
        // TODO get place and pass it to the interactor as a function parameter
        placesInteractor.deletePlace();
    }
}
