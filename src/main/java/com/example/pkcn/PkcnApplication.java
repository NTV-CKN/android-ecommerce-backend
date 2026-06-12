package com.example.pkcn;

import com.example.pkcn.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PkcnApplication {
	public static void main(String[] args) {
		SpringApplication.run(PkcnApplication.class, args);
	}

}
