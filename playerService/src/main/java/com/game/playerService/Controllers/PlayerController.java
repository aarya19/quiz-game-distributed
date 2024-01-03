package com.game.playerService.Controllers;

import com.game.common.KafkaProducerConfig;
import com.game.common.KafkaTopicConfig;
import com.game.entities.Player;
import com.game.utilities.MongoService;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.game.core.Constants.PLAYERS_JOINED;

@RestController
@RequestMapping("/player")
public class PlayerController {
    public static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    private final KafkaProducerConfig producer;
    private final KafkaTopicConfig topicConfig = new KafkaTopicConfig();
//    private final AdminClient adminClient;


    @Autowired
    public PlayerController(KafkaProducerConfig producer) {
        this.producer = producer;
    }

    @PostMapping("/join")
    public String joinGame(@RequestBody Player player) throws ExecutionException, InterruptedException {

//        mongoService.saveData(player);
        player.setPlayerId(UUID.randomUUID().toString());
        NewTopic joinedTopic = topicConfig.createTopic(PLAYERS_JOINED);
        LOGGER.info(String.format("Topic: %s", joinedTopic.name()));
        producer.setTopic(joinedTopic);
        producer.sendMessage(player);

        return "Player joined with name: " + player.getPlayerName() + " Id "+ player.getPlayerId() + " RoomId: "+player.getRoomId();
    }

//    private boolean topicExists(String topicName) throws ExecutionException, InterruptedException {
//        ListTopicsResult topics = adminClient.listTopics();
//        Set<String> topicNames = topics.names().get();
//        return topicNames.contains(topicName);
//    }
}
