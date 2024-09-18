package com.example.weatherapp;

public class WeatherRVModal {
    private String time,temperature;
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    public WeatherRVModal(String time, String temperature) {
        this.time = time;
        this.temperature = temperature;
    }
}
