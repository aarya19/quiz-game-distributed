package com.game.utilities;

import com.game.entities.Question;
import com.game.entities.Quiz;
import com.game.entities.QuizMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MongoService {

    private final MongoTemplate mongoTemplate;



    @Autowired
    public MongoService(MongoTemplate mongoTemplate) throws Exception {
        this.mongoTemplate = mongoTemplate;
    }


    public void saveData(Object data) {
        // Add any additional logic here
        mongoTemplate.save(data);
    }

    public QuizMaster findQuizMaster(String key, String value) {
//        Query query = new Query(Criteria.where(key).is(value));

        return mongoTemplate.findById(value, QuizMaster.class);
    }
    public Quiz fetchQuiz(String quizId){
        Query query = new Query(Criteria.where("_id").is(quizId));

        return mongoTemplate.findOne(query, Quiz.class);
    }

    public Question fetchQuestion(String quizId, String questionId){
        Query query = new Query(Criteria.where("_id").is(questionId+"_"+quizId));

        return mongoTemplate.findOne(query, Question.class);
    }



}
