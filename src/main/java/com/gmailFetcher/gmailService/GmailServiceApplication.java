package com.gmailFetcher.gmailService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = "com.gmailFetcher.gmailService")
public class GmailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmailServiceApplication.class, args);
	}

}
