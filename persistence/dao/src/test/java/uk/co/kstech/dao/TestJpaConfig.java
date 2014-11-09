package uk.co.kstech.dao;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by KMcGivern on 25/04/2014.
 */
@Configuration
@ComponentScan("uk.co.kstech")
@EnableAutoConfiguration
@PropertySource("classpath:/application.properties")
public class TestJpaConfig {
}
