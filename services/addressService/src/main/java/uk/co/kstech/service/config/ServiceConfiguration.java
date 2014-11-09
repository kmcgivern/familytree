package uk.co.kstech.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uk.co.kstech.service.AddressService;
import uk.co.kstech.service.AddressServiceImpl;

/**
 * Created by KMcGivern on 10/04/2014.
 */
@Configuration
@EnableTransactionManagement
public class ServiceConfiguration {

    @Bean
    public CommonAnnotationBeanPostProcessor getCommonAnnotationBeanPostProcessor(){
        return new CommonAnnotationBeanPostProcessor();
    }

//    @Bean
//    public AddressService getAddressService() {
//        return new AddressServiceImpl();
//    }
}
