package com.gestaowelinton.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })


public class ErpCoreSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpCoreSystemApplication.class, args);
	}

}
