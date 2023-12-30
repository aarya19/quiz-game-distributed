package com.game.entities;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomId;
    private QuizMaster quizMaster;
    private List<Player> players;

    public Room(){

    }
    public Room(String roomId, QuizMaster quizMaster) {
        this.roomId = roomId;
        this.quizMaster = quizMaster;
        this.players = new ArrayList<>();
    }

    public Room(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public QuizMaster getQuizMaster() {
        return quizMaster;
    }

    public void setQuizMaster(QuizMaster quizMaster) {
        this.quizMaster = quizMaster;
    }

    public String getQuizMasterName(){ return quizMaster.name;}

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }


}
