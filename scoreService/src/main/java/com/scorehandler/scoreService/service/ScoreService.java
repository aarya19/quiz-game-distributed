package com.scorehandler.scoreService.service;



import com.game.common.KafkaProducerConfig;
import com.game.common.KafkaTopicConfig;
import com.game.core.Constants;
import com.game.entities.*;
import org.springframework.stereotype.Service;

import java.util.*;

import org.apache.kafka.clients.admin.NewTopic;

import static com.game.core.Constants.SCORE_EVENTS;
@Service
public class ScoreService {
    private KafkaProducerConfig producer;

    public ScoreService(KafkaProducerConfig producer){
        this.producer=producer;
    }
    public void startScoreTaking(){
        KafkaTopicConfig topicConfig = new KafkaTopicConfig();
        NewTopic topic = topicConfig.createTopic(SCORE_EVENTS);
        producer.setTopic(topic);
        System.out.println("score-events topic has been created!");
    }

    public void sendScore(ScoreEvent scoreEvent){
        scoreEvent.setEventType(Constants.EVENT_TYPE.UPDATE_SCORE.event());
        producer.sendMessage(scoreEvent);
    }
}
