package com.sistema_escolar.sistema.escolar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SistemaEscolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaEscolarApplication.class, args);
	}
}
