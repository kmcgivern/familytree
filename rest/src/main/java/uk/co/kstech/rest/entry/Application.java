package uk.co.kstech.rest.entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by KMcGivern on 25/04/2014.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan( 
		basePackages = {"uk.co.kstech.*"} 
//	    useDefaultFilters = false,
//	    excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = PersonServiceImpl.class)},
	    )
public class Application {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Application.class, args);

    }

}