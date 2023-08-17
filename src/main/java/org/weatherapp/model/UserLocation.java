package org.weatherapp.model;

public class UserLocation {
    private String userId;
    private String lat;
    private String lon;
    private String name;
    private String country;
    private String state;

    public UserLocation(String userId, String lat, String lon, String name, String country, String state) {
        this.userId = userId;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.country = country;
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
