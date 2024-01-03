package com.game.playerService.Consumers;

import com.game.entities.Player;
import com.game.utilities.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.game.core.Constants.PLAYERS_JOINED;

@Service
public class PlayerConsumer {

    private final MongoService mongoService;

    @Autowired
    public PlayerConsumer(MongoService mongoService) {
        this.mongoService = mongoService;
    }

    @KafkaListener(topics = PLAYERS_JOINED, groupId = "player-consumer-group")
    public String consume(Player player) {
        // Perform actions on the received player object, e.g., save to MongoDB using MongoService
        mongoService.saveData(player);

        System.out.println("Player details saved: " + player);
        // Additional processing if needed
        return player.getPlayerName()+" details saved";

    }
}
