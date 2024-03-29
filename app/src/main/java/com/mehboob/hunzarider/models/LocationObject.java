package com.mehboob.hunzarider.models;

public class LocationObject {


    private String latitude;
    private String longitude;
    private String userId;


    public LocationObject() {
    }


    public LocationObject(String latitude, String longitude, String userId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
