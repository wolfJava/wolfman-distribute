package com.wolfman.concurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class ConcurrencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcurrencyApplication.class, args);
	}
}
