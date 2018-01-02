package com.example.kr_pc.crickettracker.model;

/**
 * Created by KR-PC on 01-01-2018.
 */

public class Rating {

    String username;
    String phoneNumber;
    String emailId;
    float rating;

    public Rating() {

    }

    public Rating(String username, String phoneNumber, String emailId, float rating) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public float getRating() {
        return rating;
    }
}
