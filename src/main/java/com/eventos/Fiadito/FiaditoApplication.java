package com.eventos.Fiadito;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan("com.eventos.Fiadito.models")
@EnableAutoConfiguration
public class FiaditoApplication {
	public static void main(String[] args) {
		SpringApplication.run(FiaditoApplication.class, args);
	}

	@Bean
	public CommandLineRunner mappingApplication(

	){
		return args -> {
		};
	}
}
