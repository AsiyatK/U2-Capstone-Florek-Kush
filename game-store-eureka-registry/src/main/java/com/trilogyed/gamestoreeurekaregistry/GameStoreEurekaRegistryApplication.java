package com.trilogyed.gamestoreeurekaregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class GameStoreEurekaRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameStoreEurekaRegistryApplication.class, args);
	}

}
