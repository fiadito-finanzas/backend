package com.eventos.Fiadito;

import com.eventos.Fiadito.models.Authority;
import com.eventos.Fiadito.models.AuthorityName;
import com.eventos.Fiadito.repositories.AuthorityRepository;
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
			AuthorityRepository authorityRepository
	){
		return args -> {
			authorityRepository.save(new Authority(AuthorityName.ROLE_ESTABLECIMIENTO));
			authorityRepository.save(new Authority(AuthorityName.ROLE_CLIENTE));
		};
	}
}
