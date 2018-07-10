package com.example.demo5.dto;

import com.example.demo5.map.AreaSet;

import java.io.Serializable;

public class AreaDTO implements Serializable {
    private int longitude;
    private int latitude;
    private String foreground;

    public AreaDTO() {
    }

    public AreaDTO(final AreaSet area) {
        setForeground(area.getForeground());
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
