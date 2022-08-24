package com.example.weatherapp.models;

import java.util.Date;

/**
 * WeatherApp created by vitto
 * on 2021-09-12
 */
public class User {
    private String id;
    private String name;
    private String phoneNumber;
    private Date birthDate;
    private String address;
    private String email;
    private String password;

    public User(String name, String phoneNumber, Date birthDate, String address, String email, String password) {
//        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public User() {
//        this.id = id;
        this.name = "";
        this.phoneNumber = "";
        this.birthDate = new Date();
        this.address = "";
        this.email = "";
        this.password = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
