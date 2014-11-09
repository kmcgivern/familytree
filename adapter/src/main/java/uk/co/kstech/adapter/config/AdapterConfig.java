package uk.co.kstech.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import uk.co.kstech.adapter.address.AddressAdapter;
import uk.co.kstech.adapter.address.AddressAdapterImpl;
import uk.co.kstech.service.AddressService;
import uk.co.kstech.service.AddressServiceImpl;

/**
 * Created by KMcGivern on 24/04/2014.
 */
@Configuration
public class AdapterConfig {

    @Bean
    public CommonAnnotationBeanPostProcessor getCommonAnnotationBeanPostProcessor(){
        return new CommonAnnotationBeanPostProcessor();
    }

}
