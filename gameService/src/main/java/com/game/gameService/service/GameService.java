package com.game.gameService.service;

import com.game.common.KafkaProducerConfig;
import com.game.common.KafkaTopicConfig;
import com.game.core.Constants;
import com.game.entities.GameEvent;
import com.game.entities.Question;
import com.game.entities.Quiz;
import org.springframework.stereotype.Service;

import java.util.*;

import org.apache.kafka.clients.admin.NewTopic;

import static com.game.core.Constants.QUIZ_EVENTS;
@Service
public class GameService {
    private KafkaProducerConfig producer;

    public GameService(KafkaProducerConfig producer){
        this.producer=producer;
    }
    public void startGame(Quiz quiz){

        List<Question> questions = quiz.getQuestions();
        ListIterator<Question> questionListIterator = questions.listIterator();
        KafkaTopicConfig topicConfig = new KafkaTopicConfig();
        NewTopic topic = topicConfig.createTopic(QUIZ_EVENTS);
        producer.setTopic(topic);
        GameEvent event = new GameEvent(Constants.EVENT_TYPE.START_QUIZ.event(), quiz.getQuizId());
        producer.sendMessage(event);


        Timer timer = new Timer();
        long timeout = 2000;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (questionListIterator.hasNext()){
                    event.setEventType(Constants.EVENT_TYPE.UPDATE_QUESTION.event());
                    event.setQuestion(questionListIterator.next());
                    producer.sendMessage(event);
                } else{
                    event.setEventType(Constants.EVENT_TYPE.END_QUIZ.event());
                    producer.sendMessage(event);
                    timer.cancel();
                }
            }
        }, timeout, timeout);
    }
}
