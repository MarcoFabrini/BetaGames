package com.betaGames;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class BetaGamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetaGamesApplication.class, args);
	}// main

	@Bean
    @Scope("prototype")
    Logger logger(InjectionPoint ip) {
	  return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
	}// logger

}// class
