package com.game.roomService.Controllers;

import com.game.common.KafkaProducerConfig;
import com.game.common.KafkaTopicConfig;
import com.game.entities.Room;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.UUID;

import static com.game.core.Constants.*;

@RestController
@RequestMapping("/quizroom")
public class RoomController {
    public static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);

    private KafkaProducerConfig producer;
    KafkaTopicConfig topicConfig = new KafkaTopicConfig();



    public RoomController(KafkaProducerConfig producer) {
        this.producer = producer;
    }

    //    @Autowired
//    public RoomController(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }

    @PostMapping("/create")
    public String createQuizRoom() {
        // Generate a unique ID
        String roomId = UUID.randomUUID().toString();
        NewTopic roomTopic = topicConfig.createTopic(ROOM_TOPIC);
        LOGGER.info(String.format("Topic: %s", roomTopic.name()));

        Room room = new Room(2);
        producer.setTopic(roomTopic);
        producer.sendMessage(room);

        // Return the unique ID as the response
        return "Quiz room created with ID: " + room.getRoomId();
    }
}



