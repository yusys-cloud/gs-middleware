package com.example.shutdown;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author yangzq80@gmail.com
 * @date 1/10/23
 */
@Slf4j
public class RestApis {
    public static void shutdown() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(null, getHeader());

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost/actuator/shutdown",
                request,
                String.class
        );

        handleResponse(response);
    }

    public static void shutdownConsumer() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(null, getHeader());

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:1012/actuator/shutdown",
                request,
                String.class
        );

        handleResponse(response);
    }

    public static void requestSleep(int t) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(null, getHeader());

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost/sleep?t=" + t, String.class);

        handleResponse(response);
    }

    public static void sendMessage(int n) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(null, getHeader());

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost/interval/message?n=" + n, String.class);

        handleResponse(response);
    }

    private static void handleResponse(ResponseEntity<String> response) {
        log.info("code {}", response.getStatusCode());
        System.out.println(response.getBody());
    }

    private static HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


}
