package com.example.screengo.interactor;

import android.text.format.DateUtils;
import android.util.Log;

import com.example.screengo.model.Location;
import com.example.screengo.model.LocationInfo;
import com.example.screengo.model.WeatherInfo;
import com.example.screengo.network.WeatherApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class LocationInteractor {
    private String TAG = "LocationInteractor_SG";

    public WeatherApi weatherApi;

    private List<String> sunnyWeatherStates = Arrays.asList("Clear", "Light Cloud", "Showers");
    private int cachedLocationId = -1;
    private String cachedWeatherState = null;

    @Inject
    public LocationInteractor(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public void getCoordinates() {
        // TODO: find GPS coordinates and return it (this method will probably move to the activity)
    }

    public void getLocationName() {
        // TODO: get GPS coordinates as parameter and return the name of the location (this method will probably move to the activity)
    }

    public int getLocationId(String cityName) {
        // If we already know the location id, we don't need to ask the weather api again
        if (cachedLocationId != -1) {
            Log.d(TAG, "Used cached location id:" + cachedLocationId);
            return cachedLocationId;
        }

        try {
            // Get location list from weather api
            Call<List<Location>> getLocationCall = weatherApi.getLocation(cityName);
            Response<List<Location>> response = getLocationCall.execute();
            List<Location> foundLocations = response.body();
            if (foundLocations == null || foundLocations.isEmpty()) {
                Log.e(TAG, "No locations found by the name '" + cityName + "'");
                return -1;
            }

            // Get location
            Location location = foundLocations.get(0);
            if (location == null) {
                Log.e(TAG, "First location in location list is null");
                return -1;
            }

            Log.d(TAG, "Got location id from weather API: " + location.getWoeid());

            // Get id from location
            cachedLocationId = location.getWoeid();
            return location.getWoeid();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getWeatherState(int locationId) {
        if (cachedWeatherState != null) {
            Log.d(TAG, "Used cached weather state: " + cachedWeatherState);
            return cachedWeatherState;
        }

        try {
            // Get location info from weather api
            Call<LocationInfo> getLocationInfoCall = weatherApi.getLocationInfo(locationId);
            Response<LocationInfo> response = getLocationInfoCall.execute();
            LocationInfo locationInfo = response.body();
            if (locationInfo == null) {
                Log.e(TAG, "Location info is null for id: " + locationId);
                return "unknown";
            }

            // Get todays weather info from location info
            List<WeatherInfo> weatherInfos = locationInfo.getConsolidatedWeather();
            if (weatherInfos == null || weatherInfos.isEmpty()) {
                Log.e(TAG, "Location info doesn't have weather infos (id: " + locationId + ")");
                return "unknown";
            }
            WeatherInfo todaysWeatherInfo = weatherInfos.get(0);

            // Get weather state from today's weather info
            cachedWeatherState = todaysWeatherInfo.getWeatherStateName();
            Log.d(TAG, "Got weather state from weather API: " + cachedWeatherState);
            return todaysWeatherInfo.getWeatherStateName();

        } catch (IOException e) {
            e.printStackTrace();
            return "unknown";
        }
    }

    public boolean isWeatherSunny(String weatherState) {
        return sunnyWeatherStates.contains(weatherState);
    }
}
