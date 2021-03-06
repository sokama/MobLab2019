package com.example.screengo.ui;

import android.app.Activity;
import android.util.Log;

import com.example.screengo.interactor.LocationInteractor;
import com.example.screengo.interactor.PlacesInteractor;
import com.example.screengo.model.Location;
import com.example.screengo.model.Place;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class MainPresenter extends Presenter<MainScreen> {

    private final static String TAG = "MainPresenter_SG";

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

    public void refreshWeather(final Activity activity) {
        // Network calls can't be on main thread
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // Get weather info
                String cityName = locationInteractor.getCityName(locationInteractor.getCoordinates(activity));
                Log.d(TAG, "City name: " + cityName);
                int locationId = locationInteractor.getLocationId(cityName);
                String weatherState = locationInteractor.getWeatherState(locationId);
                boolean isSunny = locationInteractor.isWeatherSunny(weatherState);

                // Refresh screen
                if (screen != null)
                    screen.showWeather(weatherState, isSunny);
            }
        });
    }

    public void refreshPlaces() {
        List<Place> places = placesInteractor.getPlaces();
        screen.showPlaces(places);
    }

    public void deletePlace(Place place) {
        placesInteractor.deletePlace(place);
    }

    public void deleteAllPlaces() {
        placesInteractor.deleteAllPlaces();
    }
}
