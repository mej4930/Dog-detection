package com.example.myapplication;

public class Alarm {
    private String time;
    private String status;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Alarm(String time, String status) {
        this.time = time;
        this.status = status;
    }
}
