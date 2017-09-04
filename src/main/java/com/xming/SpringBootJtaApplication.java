package com.xming;

import com.xming.config.PrimaryDBConfig;
import com.xming.config.SecondaryDBConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = { PrimaryDBConfig.class,SecondaryDBConfig.class })
public class SpringBootJtaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJtaApplication.class, args);
	}
}
