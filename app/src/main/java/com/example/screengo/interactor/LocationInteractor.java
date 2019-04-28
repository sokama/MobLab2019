package com.example.screengo.interactor;

import android.util.Log;

import com.example.screengo.model.Location;
import com.example.screengo.network.WeatherApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class LocationInteractor {
    private String tag = "LocationInteractor";

    public WeatherApi weatherApi;

    private List<String> sunnyWeatherStates = Arrays.asList("Clear", "Light Cloud", "Showers");
    private int cachedLocationId = -1;

    @Inject
    public LocationInteractor(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public void getCoordinates() {
        // TODO: find GPS coordinates and return it
    }

    public void getLocationName() {
        // TODO: get GPS coordinates as parameter and return the name of the location
    }

    public int getLocationId(String cityName) {
        // If we already know the location id, we don't need to ask the weather api again
        if (cachedLocationId != -1) {
            Log.d(tag, "Used cached location id:" + cachedLocationId);
            return cachedLocationId;
        }

        try {
            // Get location list from weather api
            Call<List<Location>> getLocationCall = weatherApi.getLocation(cityName);
            Response<List<Location>> response = getLocationCall.execute();
            List<Location> foundLocations = response.body();
            if (foundLocations == null || foundLocations.isEmpty()) {
                Log.e(tag, "No locations found by the name '" + cityName + "'");
                return -1;
            }

            // Get location
            Location location = foundLocations.get(0);
            if (location == null) {
                Log.e(tag, "First location in location list is null");
                return -1;
            }

            Log.d(tag, "Got location id from weather API: " + location.getWoeid());

            // Get id from location
            cachedLocationId = location.getWoeid();
            return location.getWoeid();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getWeatherState(int locationId) {
        return "Light Cloud"; // TODO: get weather state from weather api
    }

    public boolean isWeatherSunny(String weatherState) {
        return sunnyWeatherStates.contains(weatherState);
    }
}
