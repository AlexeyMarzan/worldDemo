package com.example.demo5.map;

public class Point implements Location {
    private final double longitude;
    private final double latitude;

    public Point(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
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

    public static double getDistance2(Point p1, Point p2) {
        double x1 = ((p1.getLongitude() + 180) % 360) - 180;
        double y1 = ((p1.getLatitude() + 90) % 180) - 90;
        double x2 = ((p2.getLongitude() + 180) % 360) - 180;
        double y2 = ((p2.getLatitude() + 90) % 180) - 90;
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    public double getDistance2(Point p) {
        return (getDistance2(this, p));
    }

}
