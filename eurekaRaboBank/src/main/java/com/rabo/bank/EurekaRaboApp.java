package com.rabo.bank;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableEurekaServer
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class EurekaRaboApp {

	public static void main(String[] args) {
		SpringApplication.run(EurekaRaboApp.class, args);
	}
}