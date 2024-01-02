package com.scorehandler.scoreService.Controllers;


import com.game.common.KafkaProducerConfig;
import com.game.core.Constants;
import com.game.entities.Answer;
import com.game.entities.ScoreEvent;
import com.game.utilities.MongoService;
import com.scorehandler.scoreService.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import static com.game.core.Constants.SCORE_EVENTS;


@RestController
@RequestMapping("/scoreControls")
public class ScoreController {

    private final MongoService mongoService;
    private KafkaProducerConfig producer;

    private ScoreService scoreService;


    @Autowired
    public ScoreController(MongoService mongoService, KafkaProducerConfig producer) {
        this.mongoService = mongoService;
        this.producer = producer;
        scoreService = new ScoreService(producer);
        scoreService.startScoreTaking();

    }

    @PostMapping("/addScore")
    public String addScore(@RequestBody Answer playerAnswer){
        String res = "Checking";
        int score = 0;
        if(playerAnswer.getQuestion().getCorrectAnswer().equalsIgnoreCase(playerAnswer.getAnswer())){
            ScoreEvent scorePlus = new ScoreEvent(playerAnswer.getPlayerId(), playerAnswer.getQuizId());
            scoreService.sendScore(scorePlus);
            res = "Score updated!";
        }
        else{
            res = "No Score :( ";
        }
        return res;
    }


    @GetMapping("/getFinalScores")
    public String addScore(@RequestParam String quizId){
        String res = "Getting the final player scores";

        //fetch all the players and scores from the database
        //order the player according to the score
        //send back the json to the webHook

        return res;
    }


    //add a listener to listen to scores and update in the DB.

    @KafkaListener(topics = SCORE_EVENTS, groupId = "score_events")
    public void persistScore(ScoreEvent event){
        if (event.getEventType().equals(Constants.EVENT_TYPE.UPDATE_SCORE.event())){
            System.out.println("We have recieved the answer for a player");
            System.out.println("The player id is:" + event.getPlayerId());
            System.out.println("The score update is, as always 1:");

        }
        if (event.getEventType().equals(Constants.EVENT_TYPE.END_QUIZ.event())){

            //send all the player scores to webhooks
        }
    }






}
