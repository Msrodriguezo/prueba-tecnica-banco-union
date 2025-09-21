package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PruebaTecnicaApplication {

	private static final Logger log = LoggerFactory.getLogger(PruebaTecnicaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PruebaTecnicaApplication.class, args);
		log.info("🔥 Log de prueba, debería estar en clientes-app.log");
	}

}
