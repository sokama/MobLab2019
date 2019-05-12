package com.example.screengo.model;

import com.orm.SugarRecord;

public class Place extends SugarRecord {
    public String name;
    public double longitude;
    public double latitude;
    public float radius; // In meters
    public int brightness;
    public String locationText;

    public Place() {}

    public Place(String name, double longitude, double latitude, float radius, int brightness, String locationText) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.brightness = brightness;
        this.locationText = locationText;
    }
}
