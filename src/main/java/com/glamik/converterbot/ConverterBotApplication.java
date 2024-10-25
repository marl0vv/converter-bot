package com.glamik.converterbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ConverterBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConverterBotApplication.class, args);
	}
}
