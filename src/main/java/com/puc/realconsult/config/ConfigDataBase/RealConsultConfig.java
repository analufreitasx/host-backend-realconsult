package com.puc.realconsult.config.ConfigDataBase;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.puc.realconsult.repository.realConsult",
        entityManagerFactoryRef = "realconsultEntityManager",
        transactionManagerRef = "realconsultTransactionManager"
)
public class RealConsultConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.realconsult")
    public DataSourceProperties realconsultProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource realconsultDataSource() {
        return realconsultProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean realconsultEntityManager() {

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(realconsultDataSource());
        factory.setPackagesToScan("com.puc.realconsult.model.realConsult");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaPropertyMap(props);

        return factory;
    }

    @Bean
    @Primary
    public JpaTransactionManager realconsultTransactionManager(
            @Qualifier("realconsultEntityManager") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}

