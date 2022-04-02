package com.example.LineTestBot.Message;

public class GameSet {
    private int gameId;
    private String gameName;

    public GameSet(int id, String gameName){
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

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}