package com.example.pkcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PkcnApplication {

	public static void main(String[] args) {
		SpringApplication.run(PkcnApplication.class, args);
	}

}
