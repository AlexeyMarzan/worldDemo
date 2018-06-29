package com.example.demo5;

public class Time {
    private long time;

    public Time() {
        this.time = 0;
    }

    public Time(Time time) {
        this.time = time.getTime();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void increase() {
        time++;
    }
}
