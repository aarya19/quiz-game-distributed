package com.game.playerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.game.common","com.game.entities", "com.game.utilities", "com.game.playerService"})
public class PlayerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayerServiceApplication.class, args);
    }

}
