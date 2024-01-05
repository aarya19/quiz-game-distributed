package com.scorehandler.scoreService.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.common.KafkaProducerConfig;
import com.game.core.Constants;
import com.game.entities.Answer;
import com.game.entities.Player;
import com.game.entities.ScoreEvent;
import com.game.utilities.MongoService;
import com.scorehandler.scoreService.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


import java.util.List;

import static com.game.core.Constants.SCORE_EVENTS;


@RestController
@RequestMapping("/scoreControls")
public class ScoreController {

    private final MongoService mongoService;
    private KafkaProducerConfig producer;

    private ScoreService scoreService;

    private ObjectMapper objectMapper = new ObjectMapper();


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
        if(playerAnswer.getQuestion()!=null && playerAnswer.getQuestion().getCorrectAnswer()!=null && playerAnswer.getAnswer()!=null){
            if(playerAnswer.getQuestion().getCorrectAnswer().equalsIgnoreCase(playerAnswer.getAnswer())){
            ScoreEvent scorePlus = new ScoreEvent(playerAnswer.getPlayerId(), playerAnswer.getQuizId());
            scoreService.sendScore(scorePlus);
            res = "Score updated!";
        }
            else{
                res = "No Score :( ";
            }
        }
        else{
            res = "Answer object not correct";
        }

        return res;
    }


    @GetMapping("/endQuiz/{quizId}")
    public ResponseEntity<String> getAllScores(@PathVariable String quizId) {
        String res = "Getting the final player scores";
        //fetch all the players of in the quiz from the DB.
        List<Player> playerList = mongoService.getAllPlayers(quizId);

        //sort them according to scores.
        Comparator<Player> playerComparator = Comparator.comparingInt(Player::getScore);
        Collections.sort(playerList,playerComparator);

        // Convert the list of players to JSON
        try {
            String playerListJson = objectMapper.writeValueAsString(playerList);

            // Include the JSON in the ResponseEntity
            return new ResponseEntity<>(playerListJson, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error converting player list to JSON", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //add a listener to listen to scores and update in the DB.
    @KafkaListener(topics = SCORE_EVENTS, groupId = "score_events")
    public void persistScore(ScoreEvent event){
        if (event.getEventType().equals(Constants.EVENT_TYPE.UPDATE_SCORE.event())){

            System.out.println("We have recieved the answer for a player");
            System.out.println("The player id is:" + event.getPlayerId());
            mongoService.updateScore(event.getPlayerId());
            System.out.println("The score update is, as always 1:");
        }
    }






}
