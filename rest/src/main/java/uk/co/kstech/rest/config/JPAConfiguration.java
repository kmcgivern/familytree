package uk.co.kstech.rest.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uk.co.kstech.dao.person.PersonRepository;
import uk.co.kstech.dao.person.RelationshipRepository;
import uk.co.kstech.rest.config.properties.RepositoryConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "uk.co.kstech.dao",
        includeFilters = @ComponentScan.Filter(value = {RelationshipRepository.class, PersonRepository.class}, type = FilterType.ASSIGNABLE_TYPE))
@EnableTransactionManagement
//@PropertySource("classpath:/application.properties")
public class JPAConfiguration {

    @Autowired
    private RepositoryConfiguration repositoryConfiguration;

    @Bean
    public DataSource getBasicDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(repositoryConfiguration.getDriverClassName());
        basicDataSource.setUsername(repositoryConfiguration.getUsername());
        basicDataSource.setPassword(repositoryConfiguration.getPassword());
        basicDataSource.setInitialSize(repositoryConfiguration.getConnectionPoolInitialSize());
        basicDataSource.setMaxIdle(repositoryConfiguration.getMaxIdle());
        basicDataSource.setMaxTotal(repositoryConfiguration.getMaxTotal());
        basicDataSource.setUrl(repositoryConfiguration.getUrl());
        basicDataSource.setValidationQuery(repositoryConfiguration.getValidationQuery());
        basicDataSource.setValidationQueryTimeout(repositoryConfiguration.getValidationQueryTimeout());
        basicDataSource.setDefaultQueryTimeout(repositoryConfiguration.getDefaultQueryTimeout());
        basicDataSource.setTestOnBorrow(true);
        basicDataSource.setTestOnCreate(true);
        return basicDataSource;
    }

//
//    @Bean
//    public JpaVendorAdapter jpaVendorAdapter(final Environment environment) {
//        final HibernateJpaVendorAdapter rv = new HibernateJpaVendorAdapter();
//
//        rv.setDatabase(Database.MYSQL);
//        rv.setDatabasePlatform(Driver.class.getName());
//        rv.setGenerateDdl(true);
//        rv.setShowSql(false);
//
//        return rv;
//    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public FactoryBean<EntityManagerFactory> entityManagerFactory(final Environment environment, final DataSource dataSource, final JpaVendorAdapter jpaVendorAdapter) {
        final Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.generate_statistics", "false");

        if (environment.acceptsProfiles("dev")) {
            properties.put("hibernate.hbm2ddl.auto", "update");
        }

        final LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setPersistenceUnitName("uk.co.kstech.model_resourceLocale");
        bean.setPackagesToScan("uk.co.kstech.model");
        bean.setJpaDialect(new HibernateJpaDialect());
        bean.setJpaVendorAdapter(jpaVendorAdapter);
        bean.setDataSource(dataSource);
        bean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        bean.setJpaPropertyMap(properties);
        bean.setMappingResources();
        return bean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
}