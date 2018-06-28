package com.example.demo5.map;

import java.io.Serializable;

public class CellDTO implements Serializable {
    private int longitude;
    private int latitude;
    private String foreground;

    public CellDTO() {
    }

    public CellDTO(final Cell cell) {
        setForeground(cell.getForeground());
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public String getForeground() {
        return foreground;
    }

    public void setForeground(String foreground) {
        this.foreground = foreground;
    }
}
