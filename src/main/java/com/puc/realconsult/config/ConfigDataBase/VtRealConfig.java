package com.puc.realconsult.config.ConfigDataBase;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.puc.realconsult.repository.vtRealRepository",
        entityManagerFactoryRef = "vtrealEntityManager",
        transactionManagerRef = "vtrealTransactionManager"
)
public class VtRealConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.vtreal")
    public DataSourceProperties vtrealProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource vtrealDataSource() {
        return vtrealProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean vtrealEntityManager() {

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "none");
        props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(vtrealDataSource());
        factory.setPackagesToScan("com.puc.realconsult.model.vtRealModel");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaPropertyMap(props);

        return factory;
    }

    @Bean
    public JpaTransactionManager vtrealTransactionManager(
            @Qualifier("vtrealEntityManager") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
