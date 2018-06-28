package com.example.demo5.map;

public class Location {
    private final Integer longitude;
    private final Integer latitude;

    public Location(Integer longitude, Integer latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }
}
