package com.game.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "player_details")
public class Player {

    @Id
    private String playerId;

    private String playerName;
    private int score;
    private String roomId;


    public Player(String playerName, String roomId) {
        this.playerId= UUID.randomUUID().toString();
        this.playerName = playerName;
        this.score = 0;
        this.roomId = roomId;

    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public String getPlayerId() {
        return playerId;
    }
    public String getPlayerName() {
        return playerName;
    }
    public int getScore() {
        return score;
    }
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

}
