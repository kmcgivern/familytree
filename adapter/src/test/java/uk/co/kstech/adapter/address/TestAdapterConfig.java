package uk.co.kstech.adapter.address;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import uk.co.kstech.adapter.config.AdapterConfig;
import uk.co.kstech.service.PersonService;

/**
 * Created by KMcGivern on 24/04/2014.
 */
@Configuration
@Import({AdapterConfig.class})
@EnableAutoConfiguration
@ComponentScan("uk.co.kstech.adapter.*")
public class TestAdapterConfig {


    @Bean
    public PersonService getPersonService() {
        return Mockito.mock(PersonService.class);
    }
}
