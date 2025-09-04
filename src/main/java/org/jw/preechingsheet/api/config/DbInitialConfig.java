package org.jw.preechingsheet.api.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import jakarta.annotation.PostConstruct;

@Configuration
public class DbInitialConfig {
	private final DataSource dataSource;

    public DbInitialConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void init() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("data.sql")
        );
        populator.execute(dataSource);
    }
}
