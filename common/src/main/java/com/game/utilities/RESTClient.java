package com.game.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RESTClient {

    public static ResponseEntity<String> postMessage(String url, Object data){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Assuming Answer class has proper toString or use Jackson ObjectMapper to convert to JSON
        String requestBody = null;
        try {
            requestBody = new ObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();

        // Perform the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return responseEntity;
    }



    public static ResponseEntity<String> getMessage(String url){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();

        // Perform the POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        return responseEntity;
    }


}
