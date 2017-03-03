package uk.co.kstech.rest.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.rest.service.person.RestPersonService;
import uk.co.kstech.rest.service.utilities.JsonUtils;
import uk.co.kstech.service.PersonService;

/**
 * Created by KMcGivern on 28/04/2014.
 */
@Configuration
@ComponentScan(basePackages = {  "uk.co.kstech.rest.service.person.*", "uk.co.kstech.rest.service.utilities.*"})
public class TestRestConfig {


}


