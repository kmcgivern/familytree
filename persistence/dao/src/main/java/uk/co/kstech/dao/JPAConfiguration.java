package uk.co.kstech.dao;

import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uk.co.kstech.dao.person.PersonDao;
import uk.co.kstech.dao.person.UnionDao;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "uk.co.kstech.dao",
        includeFilters = @ComponentScan.Filter(value = {UnionDao.class, PersonDao.class}, type = FilterType.ASSIGNABLE_TYPE))
@EnableTransactionManagement
@PropertySource("classpath:/application.properties")
public class JPAConfiguration {

    public static final String DATA_STORE = "data/datastore";

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
    public JpaVendorAdapter jpaVendorAdapter(final Environment environment) {
        final HibernateJpaVendorAdapter rv = new HibernateJpaVendorAdapter();

        rv.setDatabase(Database.H2);
        rv.setDatabasePlatform(H2Dialect.class.getName());
        rv.setGenerateDdl(true);
        rv.setShowSql(false);

        return rv;
    }

    @Bean
    public FactoryBean<EntityManagerFactory> entityManagerFactory(final Environment environment, final DataSource dataSource, final JpaVendorAdapter jpaVendorAdapter) {
        final Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.generate_statistics", "false");

        if (environment.acceptsProfiles("dev")) {
            properties.put("hibernate.hbm2ddl.auto", "update");
        }

        final LocalContainerEntityManagerFactoryBean rv = new LocalContainerEntityManagerFactoryBean();
        rv.setPersistenceUnitName("uk.co.kstech.model_resourceLocale");
        rv.setPackagesToScan("uk.co.kstech.model");
        rv.setJpaDialect(new HibernateJpaDialect());
        rv.setJpaVendorAdapter(jpaVendorAdapter);
        rv.setDataSource(dataSource);
        rv.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        rv.setJpaPropertyMap(properties);
        rv.setMappingResources();
        return rv;
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