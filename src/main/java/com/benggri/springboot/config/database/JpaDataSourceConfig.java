package com.benggri.springboot.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.benggri.springboot.jpa.repository" , entityManagerFactoryRef = "basicEntityManager", transactionManagerRef = "basicTransactionManager")
public class JpaDataSourceConfig {

    @Value("${spring.datasource.hikari.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.hikari.jdbc-url}")
    private String url;

    @Value("${spring.datasource.hikari.username}")
    private String userName;

    @Value("${spring.datasource.hikari.password}")
    private String password;

    @Bean
    public DataSource jpaDataSource(){
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        datasource.setDriverClassName(driverClassName);
        datasource.setUrl(url);
        datasource.setUsername(userName);
        datasource.setPassword(password);
        return datasource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean basicEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(jpaDataSource());
        em.setPackagesToScan("com.benggri.springboot.jpa.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.put("spring.jpa.properties.hibernate.show_sql"         , true);
        properties.put("spring.jpa.properties.hibernate.format_sql"       , true);
        properties.put("spring.jpa.properties.hibernate.use_sql_comments" , true);
        properties.put("hibernate.dialect"                                , "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.implicit_naming_strategy"               , "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy"); // 네이밍
        properties.put("hibernate.physical_naming_strategy"               , "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy"); // 네이밍(카멜케이스)
        return properties;
    }

    @Bean
    public PlatformTransactionManager basicTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(basicEntityManager().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
