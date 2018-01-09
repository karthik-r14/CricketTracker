package com.myapp.kr_pc.crickettracker.model;

/**
 * Created by KR-PC on 15-12-2017.
 */

public class Player {
    long playerId;
    String playerName;

    public Player(long playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public long getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }
}