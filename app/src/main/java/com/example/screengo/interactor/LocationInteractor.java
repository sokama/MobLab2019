package com.example.screengo.interactor;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.screengo.model.Location;
import com.example.screengo.model.LocationInfo;
import com.example.screengo.model.WeatherInfo;
import com.example.screengo.network.WeatherApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class LocationInteractor {
    private String TAG = "LocationInteractor_SG";

    public WeatherApi weatherApi;

    private List<String> sunnyWeatherStates = Arrays.asList("Clear", "Light Cloud", "Showers");
    private int cachedLocationId = -1;
    private String cachedWeatherState = null;

    private Activity activity;
    private LocationManager locationManager;
    private Geocoder geocoder;

    @Inject
    public LocationInteractor(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public android.location.Location getCoordinates(Activity activity) {
        if (geocoder == null || locationManager == null || this.activity != activity) {
            setupGeocoder(activity);
        }

        // Check permission
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Location permission not granted!");
            return null;
        }

        android.location.Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null)
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        return location;
    }

    public String getLocationName(android.location.Location location) {
        if (location == null) {
            Log.e(TAG, "Getting location name FAILED: location is null");
            return "Unknown location";
        }

        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        // Create address from location
        Address address = getAddress(latitude, longitude);
        if (address == null) {
            Log.e(TAG, "Getting location name FAILED: no address found");
            return "Unknown location";
        }

        return address.getAddressLine(0);
    }

    public String getCityName(android.location.Location location) {
        if (location == null) {
            Log.e(TAG, "Getting city name FAILED: location is null");
            return "Unknown city";
        }

        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        // Create address from location
        Address address = getAddress(latitude, longitude);
        if (address == null) {
            Log.e(TAG, "Getting city name FAILED: no address found");
            return "Unknown city";
        }

        return address.getLocality();
    }

    private Address getAddress(double latitude, double longitude) {
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses == null || addresses.size() < 1) {
            Log.e(TAG, "Getting address FAILED: no address found");
            return null;
        }

        return addresses.get(0);
    }

    private void setupGeocoder(Activity activity) {
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        geocoder = new Geocoder(activity, Locale.getDefault());
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
