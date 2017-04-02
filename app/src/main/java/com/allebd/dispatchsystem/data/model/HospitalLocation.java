package com.allebd.dispatchsystem.data.model;

/**
 * Created by Digz on 02/04/2017.
 */

public class HospitalLocation {
    private String uid;
    private double latitude;
    private double longitude;

    public HospitalLocation() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
