package com.elopez.mariscal.synchronizer.configs;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;

@Configuration
public class AuditorDatasourceConfig {
    
    @Bean
    @ConfigurationProperties("spring.datasource.auditor")
    public DataSourceProperties auditorDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource auditorDataSource() {
        return auditorDataSourceProperties().initializeDataSourceBuilder().build();
    }

}
