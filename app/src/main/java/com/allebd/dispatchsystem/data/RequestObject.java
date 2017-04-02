package com.allebd.dispatchsystem.data;

/**
 * Created by Digz on 02/04/2017.
 */

public class RequestObject {
    private String userId;
    private String token;
    private double latitude;
    private double longitude;

    public RequestObject() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
