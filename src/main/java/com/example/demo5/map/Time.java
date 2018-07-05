package com.example.demo5.map;

public class Time {
    private long time;

    public Time() {
        this.time = 0;
    }

    public Time(Time time) {
        this.time = time.get();
    }

    public long get() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void increase() {
        time++;
    }

    @Override
    public String toString() {
        return Long.toString(time);
    }
}
