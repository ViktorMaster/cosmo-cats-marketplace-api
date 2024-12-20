package com.cosmo.cats.marketplace.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CosmoCatsMarketplaceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CosmoCatsMarketplaceApiApplication.class, args);
	}

}
