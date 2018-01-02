package com.example.kr_pc.crickettracker.model;

/**
 * Created by KR-PC on 01-01-2018.
 */

public class Feedback {
    String username;
    String phoneNumber;
    String emailId;
    String feedback;

    public Feedback() {

    }

    public Feedback(String username, String phoneNumber, String emailId, String feedback) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.feedback = feedback;
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

    public String getFeedback() {
        return feedback;
    }
}
