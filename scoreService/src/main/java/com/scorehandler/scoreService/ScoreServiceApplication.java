package com.scorehandler.scoreService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.game.common","com.game.entities", "com.game.utilities","com.scorehandler.scoreService"})

public class ScoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreServiceApplication.class, args);
	}

}
