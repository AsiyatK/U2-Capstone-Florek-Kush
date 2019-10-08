package com.trilogyed.U2Capstoneconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class U2CapstoneConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(U2CapstoneConfigServerApplication.class, args);
	}

}
