package com.game.roomService.Controllers;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.UUID;

@RestController
@RequestMapping("/quizroom")
public class RoomController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public RoomController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/create")
    public String createQuizRoom() {
        // Generate a unique ID
        String roomId = UUID.randomUUID().toString();

        // Send the unique ID to the Kafka topic
        kafkaTemplate.send(new ProducerRecord<>("rooms", roomId));

        // Return the unique ID as the response
        return "Quiz room created with ID: " + roomId;
    }
}



