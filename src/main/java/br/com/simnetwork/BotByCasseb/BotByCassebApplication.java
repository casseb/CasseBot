package br.com.simnetwork.BotByCasseb;


import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.simnetwork.BotByCasseb.model.entity.app.Start;

@SpringBootApplication
public class BotByCassebApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BotByCassebApplication.class, args);
	}
	
}
