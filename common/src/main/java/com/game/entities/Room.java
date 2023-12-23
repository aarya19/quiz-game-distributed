package com.game.entities;

import java.util.List;

public class Room {
    private int roomId;
    private QuizMaster quizMaster;
    private List<Player> players;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public QuizMaster getQuizMaster() {
        return quizMaster;
    }

    public void setQuizMaster(QuizMaster quizMaster) {
        this.quizMaster = quizMaster;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Room(int roomId, QuizMaster quizMaster, List<Player> players) {
        this.roomId = roomId;
        this.quizMaster = quizMaster;
        this.players = players;
    }

    public Room() {
    }
}
