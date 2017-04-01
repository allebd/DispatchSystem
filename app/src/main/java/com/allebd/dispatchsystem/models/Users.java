package com.allebd.dispatchsystem.models;

/**
 * Created by CSISPC on 10/03/2017.
 */

public class Users {;
    private String username;
    private String password;
    private String telephone;
    private String dob;
    private String gender;
    private String bloodGroup;

    public Users(String username, String password, String telephone, String dob, String gender, String bloodGroup) {
        this.username = username;
        this.password = password;
        this.telephone = telephone;
        this.dob = dob;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
    }

    public Users(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
