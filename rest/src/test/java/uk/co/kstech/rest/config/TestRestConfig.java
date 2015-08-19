package uk.co.kstech.rest.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.rest.service.person.PersonService;
import uk.co.kstech.rest.service.person.RestPersonService;
import uk.co.kstech.rest.service.utilities.JsonUtils;

/**
 * Created by KMcGivern on 28/04/2014.
 */
@Configuration
@ComponentScan(basePackages = {  "uk.co.kstech.rest.service.person.*", "uk.co.kstech.rest.service.utilities.*"})
public class TestRestConfig {

    @Bean
    public PersonService getRestPersonService(){
        return new RestPersonService();
    }

    @Bean
    public uk.co.kstech.service.PersonService getPersonService() {
        return Mockito.mock(uk.co.kstech.service.PersonService.class);
    }

    @Bean
    public PersonAdapter getPersonAdapter() {
        return Mockito.mock(PersonAdapter.class);
    }

    @Bean
    public JsonUtils getJsonUtils(){
        return new JsonUtils();
    }
}


