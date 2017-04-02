package com.allebd.dispatchsystem.data.model;


public class User {
    private String uid;
    private String name;
    private String telephone;
    private String dob;
    private String gender;
    private String bloodGroup;

    public User(String name, String telephone, String dob, String gender, String bloodGroup) {
        this.name = name;
        this.telephone = telephone;
        this.dob = dob;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
    }

    public User(){

    }

    public void initEmptyUser(){
        name = "";
        telephone = "";
        dob = "";
        gender = "";
        bloodGroup = "";
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
