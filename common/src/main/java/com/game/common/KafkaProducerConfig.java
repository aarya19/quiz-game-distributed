package com.game.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.internals.Topic;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;



@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private static String bootstrapAddress;
    public static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerConfig.class);
    private NewTopic topic;

    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaProducerConfig(KafkaTemplate<String, Object> template){
        this.kafkaTemplate = template;
    }

    private static KafkaProducer<String, Object> createKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        props.put(ProducerConfig.CLIENT_ID_CONFIG, CONSUMER_APP_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonDeserializer");

        return new KafkaProducer(props);
    }

    public void setTopic(NewTopic topic) {
        this.topic = topic;
    }
    //    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }


//    @Bean
//    public ProducerFactory<String, Object> multiTypeProducerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        configProps.put(JsonSerializer.TYPE_MAPPINGS, "question:com.game.entities.Question;quiz:com.game.entities.Quiz;quizMaster:com.game.entities.QuizMaster");
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }

//    @Bean
//    public KafkaTemplate<String, Object> multiTypeKafkaTemplate() {
//        return new KafkaTemplate<>(multiTypeProducerFactory());
//    }

    public void sendMessage(Object event){
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Message<Object> message;
        //            String json = ow.writeValueAsString(event);
        LOGGER.info(String.format("Topic: %s", this.topic.name()));
//        try(KafkaProducer<String, Object> producer = createKafkaProducer()){
            message = MessageBuilder.withPayload(event).setHeader(KafkaHeaders.TOPIC, this.topic.name()).build();
//            producer.send(new ProducerRecord<String, Object>(KafkaHeaders.TOPIC, "1", message));
//        }

        this.kafkaTemplate.send(message);
        LOGGER.info(String.format("New event sent: %s", event.toString()));
    }

}