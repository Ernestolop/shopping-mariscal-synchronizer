package com.elopez.mariscal.synchronizer.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.elopez.mariscal.synchronizer.modules.auditor.gateway", entityManagerFactoryRef = "auditorEntityManagerFactory", transactionManagerRef = "auditorTransactionManager")
public class AuditorDatasourceJpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean auditorEntityManagerFactory(
            @Qualifier("auditorDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages("com.elopez.mariscal.synchronizer.modules.auditor.gateway")
                .persistenceUnit("auditor")
                .build();
    }

    @Bean
    public PlatformTransactionManager auditorTransactionManager(
            @Qualifier("auditorEntityManagerFactory") LocalContainerEntityManagerFactoryBean auditorEntityManagerFactory) {
        return new JpaTransactionManager(auditorEntityManagerFactory.getObject());
    }

}
