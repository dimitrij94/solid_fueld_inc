package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.example"})
public class SolidFuelIncApplication {
	public static void main(String[] args) {
		SpringApplication.run(SolidFuelIncApplication.class, args);
	}
}
