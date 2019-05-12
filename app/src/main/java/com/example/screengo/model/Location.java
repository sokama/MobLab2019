package com.example.screengo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("location_type")
    @Expose
    private String locationType;

    @SerializedName("woeid")
    @Expose
    private int woeid;

    @SerializedName("latt_long")
    @Expose
    private String lattLong;

    public String getTitle() {
        return title;
    }

    // Getters, setters

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public int getWoeid() {
        return woeid;
    }

    public void setWoeid(int woeid) {
        this.woeid = woeid;
    }

    public String getLattLong() {
        return lattLong;
    }

    public void setLattLong(String lattLong) {
        this.lattLong = lattLong;
    }
}
