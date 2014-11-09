package uk.co.kstech.rest.config;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import uk.co.kstech.adapter.address.AddressAdapter;
import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.dto.address.AddressDTO;
import uk.co.kstech.rest.entry.Application;
import uk.co.kstech.rest.service.address.RestAddressService;
import uk.co.kstech.rest.service.person.PersonService;
import uk.co.kstech.rest.service.person.RestPersonService;
import uk.co.kstech.rest.service.utilities.JsonUtils;
import uk.co.kstech.service.AddressService;

/**
 * Created by KMcGivern on 28/04/2014.
 */
@Configuration
@ComponentScan(basePackages = { "uk.co.kstech.rest.service.address.*", "uk.co.kstech.rest.service.person.*", "uk.co.kstech.rest.service.utilities.*"})
public class TestRestConfig {

    @Bean
    public uk.co.kstech.rest.service.address.AddressService getRestAddressService(){
        return new RestAddressService();
    }

    @Bean
    public PersonService getRestPersonService(){
        return new RestPersonService();
    }

    @Bean
    public AddressService getAddressService() {
        return Mockito.mock(AddressService.class);
    }

    @Bean
    public uk.co.kstech.service.PersonService getPersonService() {
        return Mockito.mock(uk.co.kstech.service.PersonService.class);
    }

    @Bean
    public AddressAdapter getAddressAdapter() {
        return Mockito.mock(AddressAdapter.class);
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


