package com.example.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Dmitrij on 24.07.2016.
 */
@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@PropertySource(value = {"classpath:application.properties"})
@EnableJpaRepositories(basePackages = "com.example.repositories")
public class PersistenceConfig {
    @Autowired
    private Environment environment;


    /*
        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(environment.getRequiredProperty("dataSource.driverClassName"));

            dataSource.setUrl(environment.getRequiredProperty("dataSource.url"));
            dataSource.setUsername(environment.getRequiredProperty("dataSource.username"));
            dataSource.setPassword(environment.getRequiredProperty("dataSource.password"));
            return dataSource;
        }
    */
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(environment.getRequiredProperty("dataSource.driverClassName"));
        config.setJdbcUrl(environment.getRequiredProperty("dataSource.url"));
        config.setUsername(environment.getRequiredProperty("dataSource.username"));
        config.setPassword(environment.getRequiredProperty("dataSource.password"));
        config.addDataSourceProperty("useServerPrepStmts", environment.getRequiredProperty("dataSource.useServerPrepStmts"));
        config.addDataSourceProperty("cachePrepStmts", environment.getRequiredProperty("dataSource.cachePrepStmts"));
        config.addDataSourceProperty("prepStmtCacheSize", environment.getRequiredProperty("dataSource.prepStmtCacheSize"));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", environment.getRequiredProperty("dataSource.prepStmtCacheSqlLimit"));
        return new HikariDataSource(config);
    }
    /*@Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.H2) //.H2 or .DERBY
                .build();
    }*/

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("com.example.domain");
        factory.setJpaVendorAdapter(jpaVendorAdapter());
        factory.setJpaProperties(jpaProperties());
        return factory;
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));

        return properties;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter() {{
            setGenerateDdl(true);
        }};
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

}
