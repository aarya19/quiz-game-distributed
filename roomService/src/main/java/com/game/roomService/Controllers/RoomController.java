package com.game.roomService.Controllers;

import com.game.common.KafkaProducerConfig;
import com.game.common.KafkaTopicConfig;
import com.game.entities.QuizMaster;
import com.game.entities.Room;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.game.core.Constants.*;
@RestController
@RequestMapping("/quizroom")
public class RoomController {
    public static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);

    private final KafkaProducerConfig producer;
    private final KafkaTopicConfig topicConfig = new KafkaTopicConfig();
    private final AdminClient adminClient;

    @Autowired
    public RoomController(KafkaProducerConfig producer, AdminClient adminClient) {
        this.producer = producer;
        this.adminClient = adminClient;
    }

    @PostMapping("/create")
    public String createQuizRoom(@RequestBody Room room) throws ExecutionException, InterruptedException {
        // Generate a unique ID
        // String roomId = UUID.randomUUID().toString();

        // Check if the topic exists
        if (!topicExists(ROOM_TOPIC)) {
            NewTopic roomTopic = topicConfig.createTopic(ROOM_TOPIC);
            LOGGER.info(String.format("Topic: %s", roomTopic.name()));
            producer.setTopic(roomTopic);
        }

        // Room room = new Room(roomId, quizMaster, new ArrayList<>());
        producer.sendMessage(room);

        // Return the unique ID as the response
        return "Quiz room created with ID: " + room.getRoomId() + " Quiz master is " + room.getQuizMasterName();
    }

    private boolean topicExists(String topicName) throws ExecutionException, InterruptedException {
        ListTopicsResult topics = adminClient.listTopics();
        Set<String> topicNames = topics.names().get();
        return topicNames.contains(topicName);
    }
}
