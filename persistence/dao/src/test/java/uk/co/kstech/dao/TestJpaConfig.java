package uk.co.kstech.dao;

import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KMcGivern on 25/04/2014.
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "uk.co.kstech.dao")
@PropertySource("classpath:application.properties")
public class TestJpaConfig {

    public static final String DATA_STORE = "data/datastore";

    @Bean
    public javax.validation.Validator validator() {
        return new LocalValidatorFactoryBean();
    }



    /**
     * Configures a file based datastore
     *
     * @param datastoreBaseDirectoryPath
     * @return
     */
    @Bean
    public File datastoreBaseDirectory(final @Value("${spring.datastore-base-directory:${user.dir}/var/dev}") String datastoreBaseDirectoryPath) {
        final File rv = new File(datastoreBaseDirectoryPath);
        if (!(rv.isDirectory() || rv.mkdirs())) {
            throw new RuntimeException(String.format("Could not initialize '%s' as base directory for datastore!", rv.getAbsolutePath()));
        }

        new File(rv, DATA_STORE).mkdirs();
        return rv;
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(final Environment environment) {
        final HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        adapter.setDatabase(Database.H2);
        adapter.setDatabasePlatform(H2Dialect.class.getName());
        adapter.setGenerateDdl(true);
        adapter.setShowSql(false);

        return adapter;
    }

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

        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("uk.co.kstech.model_resourceLocale");
        factoryBean.setPackagesToScan("uk.co.kstech.model");
        factoryBean.setJpaDialect(new HibernateJpaDialect());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setDataSource(dataSource);
        factoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        factoryBean.setJpaPropertyMap(properties);
        factoryBean.setMappingResources();
        return factoryBean;
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
