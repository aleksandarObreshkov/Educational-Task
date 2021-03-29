package com.example.config;

import org.eclipse.persistence.jpa.PersistenceProvider;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import javax.sql.DataSource;

@Configuration
public class PersistenceConfig {

    @Bean
    public DataSource postgreDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName("starwarsdb");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean factoryBean() {
        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(postgreDataSource());
        factory.setPersistenceProviderClass(PersistenceProvider.class);
        return factory;
    }
}
