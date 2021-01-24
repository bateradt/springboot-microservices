package com.monitora.aulamicroservices.course;

import com.monitora.aulamicroservices.core.property.JwtConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@SpringBootApplication
@EntityScan({"com.monitora.aulamicroservices.core.model"})
@EnableJpaRepositories({"com.monitora.aulamicroservices.core.repository"})
@EnableConfigurationProperties(value = JwtConfiguration.class)
@ComponentScan("com.monitora.aulamicroservices")
public class CourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseApplication.class, args);
	}

}
