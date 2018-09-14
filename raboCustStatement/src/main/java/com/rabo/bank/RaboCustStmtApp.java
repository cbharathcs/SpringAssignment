package com.rabo.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class RaboCustStmtApp {
	public static void main(String[] args) {
		SpringApplication.run(RaboCustStmtApp.class, args);
	}
}
