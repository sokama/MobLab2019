package com.example.screengo.interactor;

import com.example.screengo.model.Place;

import java.util.List;

import javax.inject.Inject;

public class PlacesInteractor {
    @Inject
    public PlacesInteractor() {}

    public List<Place> getPlaces() {
        return Place.listAll(Place.class);
    }

    public void addPlace(Place place) {
        place.save();
        // TODO refresh recycler view / place list?
    }

    public void deletePlace(Place place) {
        place.delete();
        // TODO refresh recycler view / place list?
    }

    public void deleteAllPlaces() {
        Place.deleteAll(Place.class);
        // TODO refresh recycler view / place list?
    }
}
