package com.myapp.kr_pc.crickettracker.model;

/**
 * Created by KR-PC on 20-12-2017.
 */

public class Fixture {
    String name;
    String date;

    public Fixture(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
