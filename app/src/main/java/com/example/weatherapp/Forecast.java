package com.example.weatherapp;

import android.util.Log;

public class Forecast {
    private String temp;
    private String main;
    private String time;
    private String icon;

    public Forecast(String temp, String main, String time,String icon) {
        this.temp = temp;
        this.main = main;
        this.time = time;
        this.icon = icon;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
