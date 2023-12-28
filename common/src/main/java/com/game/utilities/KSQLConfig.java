package com.game.utilities;

import com.game.common.KafkaProducerConfig;
import com.game.core.Constants;
import com.game.entities.GameEvent;
import com.game.entities.Question;
import io.confluent.ksql.api.client.*;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.awt.desktop.QuitEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Configuration
public class KSQLConfig {

    private static String KSQLDB_SERVER_HOST = "localhost";
    private static int KSQLDB_SERVER_HOST_PORT = 8088;
//    public static final Logger LOGGER = LoggerFactory.getLogger(KSQLConfig.class);
    Map<String, Object> properties = Collections.singletonMap(
            "auto.offset.reset", "earliest"
    );

    private String QUIZ_EVENTS_STREAM = String.format("""
            CREATE STREAM quiz_events_stream (eventType VARCHAR KEY, quizId VARCHAR,
             question STRUCT<id VARCHAR,
                             questionStr VARCHAR,
                             quizId VARCHAR,
                             options MAP<STRING, STRING>,
                             correctAnswer VARCHAR
                             >,
             response MAP<STRING, STRING>,
             timestamp VARCHAR)
              WITH (KAFKA_TOPIC = %s,
                    VALUE_FORMAT = 'JSON',
                    TIMESTAMP = 'timestamp',
                    TIMESTAMP_FORMAT = 'yyyy.MM.dd.HH.mm.ss',
                    PARTITIONS = 1);
            """, Constants.QUIZ_EVENTS);

    private static final String CREATE_QUIZ_EVENTS_TABLE = """
            CREATE TABLE quiz_events AS
            select eventType, quizId, question['questionStr'], question['correctAnswer'], timestamp, question['id'] as qId
            from quiz_events_stream
            WINDOW TUMBLING (SIZE 30 MINUTES)
            EMIT CHANGES;
            """;


    public Client ksqlClient() {
        ClientOptions options = ClientOptions.create()
                .setHost(KSQLDB_SERVER_HOST)
                .setPort(KSQLDB_SERVER_HOST_PORT);
        return Client.create(options);
    }

    public Client getQuizEventStreamClient(){
        try(Client client = ksqlClient()){
            CompletableFuture<ExecuteStatementResult> quiz_events_stream =
                    client.executeStatement(QUIZ_EVENTS_STREAM, properties);
            return client;
        }
    }

    public CompletableFuture<ExecuteStatementResult> createQuizEventsTable(Client client) {
        return client.executeStatement(CREATE_QUIZ_EVENTS_TABLE, properties);
    }

    public List<GameEvent> streamQuizEvents(Client client, String query) throws ExecutionException, InterruptedException {

        StreamedQueryResult sqr = client.streamQuery(query, properties).get();
        Row row;
        List<GameEvent> l = new ArrayList<>();
        while ((row = sqr.poll()) != null) {
            l.add(mapRowToQuizEvent(row));
        }
        return l;
    }

    private GameEvent mapRowToQuizEvent(Row row) {
        GameEvent event = new GameEvent();
        Question question = new Question();
        event.setEventType(row.getString("eventType"));
        event.setQuizId(row.getString("quizId"));
        question.setQuestionStr(row.getString("questionStr"));
        question.setId(row.getString("qId"));
        question.setCorrectAnswer(row.getString("correctAnswer"));

        event.setQuestion(question);
        return event;
    }

    public CompletableFuture<ExecuteStatementResult> executeKSQLStatement(Client client, String query) {
        return client.executeStatement(query, properties);
    }

}
