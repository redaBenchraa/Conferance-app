package com.spring.tdd;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Profile("dev")
public class PostgreConfiguration {
	@Primary
	@Bean
	public DataSource dataSource() {
		String url = "jdbc:postgresql://localhost:5432/conference_app";
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername("postgres");
		dataSource.setPassword("Welcome");
		return dataSource;
	}
}