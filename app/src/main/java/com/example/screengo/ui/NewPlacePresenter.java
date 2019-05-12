package com.example.screengo.ui;

import android.app.Activity;
import android.location.Location;

import com.example.screengo.interactor.LocationInteractor;
import com.example.screengo.interactor.PlacesInteractor;
import com.example.screengo.model.Place;

import javax.inject.Inject;

public class NewPlacePresenter extends Presenter<NewPlaceScreen>{
    public PlacesInteractor placesInteractor;
    public LocationInteractor locationInteractor;

    @Inject
    public NewPlacePresenter(PlacesInteractor placesInteractor, LocationInteractor locationInteractor) {
        this.placesInteractor = placesInteractor;
        this.locationInteractor = locationInteractor;
    }

    @Override
    public void attachScreen(NewPlaceScreen screen) {
        super.attachScreen(screen);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }

    public void addPlace(Place place) {
        placesInteractor.addPlace(place);
    }

    public void refreshLocation(Activity activity) {
        Location location = locationInteractor.getCoordinates(activity);
        String locationName = locationInteractor.getLocationName(location);
        screen.showLocation(locationName);
    }
}
