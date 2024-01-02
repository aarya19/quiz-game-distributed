package com.game.masterService.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.common.KafkaProducerConfig;
import com.game.entities.Answer;
import com.game.utilities.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/main")
public class MasterController {

    @Autowired
    public MasterController() {

    }

    @Value("${addscore.endpoint.url}")
    private String addScoreEndpoint;
    @PostMapping("/addScore")
    public RedirectView addScore(@RequestBody Answer playerAnswer){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Assuming Answer class has proper toString or use Jackson ObjectMapper to convert to JSON
        String requestBody = null;
        try {
            requestBody = new ObjectMapper().writeValueAsString(playerAnswer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        // Perform the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                addScoreEndpoint,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Check the response if needed
        System.out.println("Response from external service: " + responseEntity.getBody());

        RedirectView redirectView = new RedirectView();
        // Set the URL to redirect to after the POST request
        redirectView.setUrl("https://your-redirect-url");

        return redirectView;

    }

}
