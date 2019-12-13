package com.spring.tdd;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Profile("test")
public class H2Configuration {
	@Primary
	@Bean
	public DataSource dataSource() {
		String url = "jdbc:h2:mem:db;"
				+ "DB_CLOSE_DELAY=-1;"
				+ "CASE_INSENSITIVE_IDENTIFIERS=TRUE;"
				+ "DATABASE_TO_LOWER=TRUE;" + "MODE=MySQL";
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl(url);
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		return dataSource;
	}
}