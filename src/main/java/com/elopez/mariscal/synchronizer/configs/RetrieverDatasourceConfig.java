package com.elopez.mariscal.synchronizer.configs;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;


public class RetrieverDatasourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.retriever")
    public DataSourceProperties retrieverDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource retrieverDataSource() {
        return retrieverDataSourceProperties().initializeDataSourceBuilder().build();
    }

}
