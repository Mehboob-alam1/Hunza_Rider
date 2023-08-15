package com.mehboob.hunzarider.models;

public class Availability {


    private boolean isAvailable;
    private String userId;


    public Availability(boolean isAvailable, String userId) {
        this.isAvailable = isAvailable;
        this.userId = userId;
    }

    public Availability() {
    }


    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
