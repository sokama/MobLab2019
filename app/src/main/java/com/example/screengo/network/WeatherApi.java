package com.example.screengo.network;

import com.example.screengo.model.Location;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("location/search/")
    Call<List<Location>> getLocation(@Query("query") String locationName);
}
