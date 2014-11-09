package uk.co.kstech.rest.service.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by KMcGivern on 7/18/2014.
 */
@Component
public class JsonUtils<T> {

    public String convertToJson(final T dto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(dto);
    }

    public  T convertToPersonDTO(final Class clazz, final String json) throws IOException {
        ObjectReader ow = new ObjectMapper().reader(clazz.getClass());
        return (T) ow.readValue(json);
    }
}


