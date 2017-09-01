package com.example.scanthis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ScanThisApplication {

	private static final String HELLO_WORLD = "Hello world!";
	@GetMapping("/")
	public String helloWorld() {
		return HELLO_WORLD;
	}

	public static void main(String[] args) {
		SpringApplication.run(ScanThisApplication.class, args);
	}
}
