package com.example.demo5.map;

public class Point {
    private final Integer longitude;
    private final Integer latitude;

    public Point(Integer longitude, Integer latitude) {
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
