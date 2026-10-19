package com.learn.hello_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ConfigurationPropertiesScan   // 启用 @ConfigurationProperties 绑定
@EnableFeignClients
public class HelloApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloApiApplication.class, args);
	}

}
