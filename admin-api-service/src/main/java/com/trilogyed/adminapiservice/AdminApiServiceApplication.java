package com.trilogyed.adminapiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class AdminApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApiServiceApplication.class, args);
	}

}
