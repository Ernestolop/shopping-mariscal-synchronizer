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
@EnableJpaRepositories(basePackages = "com.elopez.mariscal.synchronizer.modules.retriever.gateway", entityManagerFactoryRef = "retrieverEntityManagerFactory", transactionManagerRef = "retrieverTransactionManager")
public class RetrieverDatasourceJpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean retrieverEntityManagerFactory(
            @Qualifier("retrieverDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages("com.elopez.mariscal.synchronizer.modules.retriever.gateway")
                .persistenceUnit("retriever")
                .build();
    }

    @Bean
    public PlatformTransactionManager retrieverTransactionManager(
            @Qualifier("retrieverEntityManagerFactory") LocalContainerEntityManagerFactoryBean retrieverEntityManagerFactory) {
        return new JpaTransactionManager(retrieverEntityManagerFactory.getObject());
    }

}
