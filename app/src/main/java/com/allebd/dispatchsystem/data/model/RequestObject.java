package com.allebd.dispatchsystem.data.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Digz on 02/04/2017.
 */

public class RequestObject {
    private String uid;
    private String userId;
    private String hospitalId;
    private String hospitalName;
    private String token;
    private double latitude;
    private double longitude;
    private long status;
    private String ambulanceId;
    private Date date;

    public RequestObject() {
    }

    public void initEmptyRequest(){
        this.userId = "";
        this.hospitalId = "";
        this.hospitalName = "";
        this.token = "";
        this.ambulanceId = "";
        this.latitude = 0;
        this.longitude = 0;
        this.status = 0;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
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

    public int getStatus() {
        return (int) status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        if (status == 1) return "Approved";
        if (status == -1) return "Unapproved";
        return "Pending";
    }

    public String  getAmbulanceId() {
        return ambulanceId;
    }

    public void setAmbulanceId(String ambulanceId) {
        this.ambulanceId = ambulanceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        String dayOfTheWeek = (String) android.text.format.DateFormat.format("EEEE", date);//Thursday
        String stringMonth = (String) android.text.format.DateFormat.format("MMM", date); //Jun
//        String intMonth = (String) android.text.format.DateFormat.format("MM", date); //06
        String year = (String) android.text.format.DateFormat.format("yyyy", date); //2013
        String day = (String) android.text.format.DateFormat.format("dd", date); //20
        String hour = (String) android.text.format.DateFormat.format("hh", date); //20
        String minute = (String) android.text.format.DateFormat.format("mm", date); //20
        return hour +":" + minute + "  " + dayOfTheWeek + " " + day + " " + stringMonth + " " + year;
    }
}
