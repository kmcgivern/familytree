package uk.co.kstech.rest.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uk.co.kstech.rest.config.properties.RepositoryConfiguration;

/**
 * Created by KMcGivern on 24/04/2014.
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({RepositoryConfiguration.class})
@ComponentScan(value = {
        "uk.co.kstech"}
)
public class RestConfiguration {


}
