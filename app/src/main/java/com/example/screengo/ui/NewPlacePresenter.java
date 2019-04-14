package com.example.screengo.ui;

import com.example.screengo.interactor.LocationInteractor;
import com.example.screengo.interactor.PlacesInteractor;

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

    public void addPlace() {
        // TODO: get place and pass it to the interactor as a function parameter
        placesInteractor.addPlace();
    }

    public void refreshLocation() {
        locationInteractor.getCoordinates();
        // TODO: pass coordinates as parameter
        locationInteractor.getLocationName();
        // TODO: pass location name as parameter
        screen.showLocation("asd");
    }
}
