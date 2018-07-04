package com.example.demo5.map;

public interface Location<T extends Location> {
    double getDistance2(T location);
}
