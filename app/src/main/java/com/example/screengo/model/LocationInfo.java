package com.example.screengo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class LocationInfo {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("location_type")
    @Expose
    private String locationType;

    @SerializedName("latt_long")
    @Expose
    private String lattLong;

    @SerializedName("sun_rise")
    @Expose
    private Date sunRise;

    @SerializedName("sun_set")
    @Expose
    private Date sunSet;

    @SerializedName("timezone_name")
    @Expose
    private String timezoneName;

    @SerializedName("parent")
    @Expose
    private Location parent;

    @SerializedName("consolidated_weather")
    @Expose
    private List<WeatherInfo> consolidatedWeather;

    // Getters, setters

    public Date getSunSet() {
        return sunSet;
    }

    public void setSunSet(Date sunSet) {
        this.sunSet = sunSet;
    }

    public String getTimezoneName() {
        return timezoneName;
    }

    public void setTimezoneName(String timezoneName) {
        this.timezoneName = timezoneName;
    }

    public Location getParent() {
        return parent;
    }

    public void setParent(Location parent) {
        this.parent = parent;
    }

    public List<WeatherInfo> getConsolidatedWeather() {
        return consolidatedWeather;
    }

    public void setConsolidatedWeather(List<WeatherInfo> consolidatedWeather) {
        this.consolidatedWeather = consolidatedWeather;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLattLong() {
        return lattLong;
    }

    public void setLattLong(String lattLong) {
        this.lattLong = lattLong;
    }

    public Date getSunRise() {
        return sunRise;
    }

    public void setSunRise(Date sunRise) {
        this.sunRise = sunRise;
    }
}
