package com.example.screengo.ui;

import com.example.screengo.model.Place;

import java.util.List;

public interface MainScreen {
    void showWeather(String weatherText, boolean isSunny);
    void showPlaces(List<Place> places);
}
