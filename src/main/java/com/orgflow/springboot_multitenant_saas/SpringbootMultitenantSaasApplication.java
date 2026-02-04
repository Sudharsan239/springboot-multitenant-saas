package com.orgflow.springboot_multitenant_saas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootMultitenantSaasApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(SpringbootMultitenantSaasApplication.class, args);
	}

}
