package com.example.kr_pc.crickettracker.model;

/**
 * Created by KR-PC on 04-12-2017.
 */

public class Match {
    long id;
    String team1;
    String team2;

    public Match(long id, String team1, String team2) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
    }

    public long getId() {
        return id;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }
}