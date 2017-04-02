package com.allebd.dispatchsystem.data.model;



public class Hospital {
    private String uid;
    private String name;
    private String address;
    private String number;
    private String token;
    private long ambulanceNumber;


    public Hospital() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getAmbulanceNumber() {
        return ambulanceNumber;
    }

    public void setAmbulanceNumber(long ambulanceNumber) {
        this.ambulanceNumber = ambulanceNumber;
    }
}