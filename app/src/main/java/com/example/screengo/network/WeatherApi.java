package com.example.screengo.network;

import com.example.screengo.model.Location;
import com.example.screengo.model.LocationInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("location/search/")
    Call<List<Location>> getLocation(@Query("query") String locationName);

    @GET("location/{woeid}/")
    Call<LocationInfo> getLocationInfo(@Path("woeid") int locationId);
}
