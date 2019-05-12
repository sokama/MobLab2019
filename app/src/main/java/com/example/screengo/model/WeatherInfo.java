package com.example.screengo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class WeatherInfo {
    @SerializedName("applicable_date")
    @Expose
    private Date applicableDate;

    @SerializedName("weather_state_name")
    @Expose
    private String weatherStateName;

    @SerializedName("weather_state_abbr")
    @Expose
    private String weatherStateAbbr;

    @SerializedName("predictability")
    @Expose
    private int predictability;

    // Getters, setters

    public String getWeatherStateName() {
        return weatherStateName;
    }

    public void setWeatherStateName(String weatherStateName) {
        this.weatherStateName = weatherStateName;
    }

    public String getWeatherStateAbbr() {
        return weatherStateAbbr;
    }

    public void setWeatherStateAbbr(String weatherStateAbbr) {
        this.weatherStateAbbr = weatherStateAbbr;
    }

    public int getPredictability() {
        return predictability;
    }

    public void setPredictability(int predictability) {
        this.predictability = predictability;
    }

    public Date getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(Date applicableDate) {
        this.applicableDate = applicableDate;
    }
}
