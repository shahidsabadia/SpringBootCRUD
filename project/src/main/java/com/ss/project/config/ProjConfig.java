/*package com.ss.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class ProjConfig {

	@Value("${db.url}")
	private String dbUrl;

	@Value("${db.classname}")
	private String dbClassName;

	@Value("${db.username}")
	private String dbUserName;

	@Value("${db.password}")
	private String dbPassword;

	@Bean
	public DriverManagerDataSource getDataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		System.out.println("DB: " + dbClassName);
		ds.setDriverClassName(dbClassName);
		ds.setPassword(dbPassword);
		ds.setUsername(dbUserName);
		ds.setUrl(dbUrl);
		return ds;
	}

}
*/