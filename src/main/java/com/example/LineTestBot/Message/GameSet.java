package com.example.LineTestBot.Message;

public class GameSet {
    private double gameId;
    private String gameName;

    public GameSet(double id, String gameName){
        this.gameId = id;
        this.gameName = gameName;
    }

    @Override
    public String toString() {
        return "GameSet{" +
                "gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                '}';
    }

    public double getGameId() {
        return gameId;
    }

    public void setGameId(double gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
