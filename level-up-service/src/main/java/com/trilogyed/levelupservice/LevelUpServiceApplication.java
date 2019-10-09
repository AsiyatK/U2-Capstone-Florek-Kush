package com.trilogyed.levelupservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LevelUpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LevelUpServiceApplication.class, args);
	}

}
