package com.example.demo5.map;

public class Point implements Location {
    private final int longitude;
    private final int latitude;

    public Point(int longitude, int latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Point{");
        sb.append("longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append('}');
        return sb.toString();
    }
}
