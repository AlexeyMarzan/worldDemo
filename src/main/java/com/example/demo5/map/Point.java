package com.example.demo5.map;

public class Point implements Location<Point> {
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
        double x1 = Math.min(p1.getLongitude(),p2.getLongitude());
        double y1 = Math.min(p1.getLatitude(),p2.getLatitude());
        double x2 = Math.max(p1.getLongitude(),p2.getLongitude());
        double y2 = Math.max(p1.getLatitude(),p2.getLatitude());

        double dx = Math.min(x2-x1,x1+360-x2);
        double dy = Math.min(y2-y1,y1+180-y2);
        return dx*dx + dy*dy;
        //double sinDy = Math.sin((y2 - y1) / 2);
        //double sinDx = Math.sin((x2 - x1) / 2);
        //return 2 * Math.asin(Math.sqrt(sinDy * sinDy + Math.cos(y1) * Math.cos(y2) * sinDx * sinDx));
    }

    @Override
    public double getDistance2(Point p) {
        return (getDistance2(this, p));
    }

}
