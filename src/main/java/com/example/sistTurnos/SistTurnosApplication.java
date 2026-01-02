package com.example.sistTurnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@EntityScan(basePackages = "com.example.sistTurnos.model")
@SpringBootApplication
public class SistTurnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistTurnosApplication.class, args);

		System.out.println("Hola Mundo");
	}

}
