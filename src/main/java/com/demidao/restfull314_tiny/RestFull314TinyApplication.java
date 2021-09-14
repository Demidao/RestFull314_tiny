package com.demidao.restfull314_tiny;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Set;

@SpringBootApplication
public class RestFull314TinyApplication {

    private static RestTemplate rt;
    private static HttpHeaders headers;
    private static final String url = "http://91.241.64.178:7081" + "/api/users";

    public RestFull314TinyApplication() {
    }

    @Autowired
    public RestFull314TinyApplication(RestTemplate rt, HttpHeaders headers) {
        this.rt = rt;
        this.headers = headers;
    }

    public static void main(String[] args) {
        SpringApplication.run(RestFull314TinyApplication.class, args);

        ResponseEntity<User[]> response = rt.getForEntity(url, User[].class);
        String sessionID = response.getHeaders().getFirst("Set-Cookie");
        headers.set("Cookie", sessionID);

        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 30);

        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> resp = rt.exchange(url, HttpMethod.POST, entity, String.class);

        System.out.println("Первая часть кода " + resp.getBody());

        User user1 = new User();
        user1.setId(3L);
        user1.setName("Thomas");
        user1.setLastName("Shelby");
        user1.setAge((byte) 30);

        HttpEntity<User> entity2 = new HttpEntity<>(user1, headers);
        ResponseEntity<String> resp2 = rt.exchange(url, HttpMethod.PUT, entity2, String.class);

        System.out.println("Вторая часть кода " + resp2.getBody());

        HttpEntity<User> entity3 = new HttpEntity<>(user1, headers);
        ResponseEntity<String> resp3 = rt.exchange(url + "/3", HttpMethod.DELETE, entity3, String.class);

        System.out.println("Третья часть кода " + resp3.getBody());
    }

}
