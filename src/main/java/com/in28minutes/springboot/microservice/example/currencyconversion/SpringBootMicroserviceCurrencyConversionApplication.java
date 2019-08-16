package com.in28minutes.springboot.microservice.example.currencyconversion;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.in28minutes.springboot.microservice.example.currencyconversion")
/*@EnableDiscoveryClient*/
public class SpringBootMicroserviceCurrencyConversionApplication {

	@PostConstruct
	public void init()
	{
		// Setting Spring Boot TimeZone
		TimeZone.setDefault( TimeZone.getTimeZone( "IST" ) );
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMicroserviceCurrencyConversionApplication.class, args);
	}
}
