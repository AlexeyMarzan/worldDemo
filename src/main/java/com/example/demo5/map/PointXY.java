package com.example.demo5.map;

public class PointXY implements Location<PointXY> {
    private final double x;
    private final double y;

    public PointXY(double x, double y) {
        this.x= x;
        this.y= y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PointXY{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }

    public static double getDistance2(PointXY p1, PointXY p2) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    @Override
    public double getDistance2(PointXY location) {
        return getDistance2(this, location);
    }
}
