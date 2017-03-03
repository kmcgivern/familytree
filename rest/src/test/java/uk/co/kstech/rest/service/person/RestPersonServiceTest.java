package uk.co.kstech.rest.service.person;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.rest.exceptions.RestExceptionHandler;
import uk.co.kstech.rest.service.utilities.DtoBuilder;
import uk.co.kstech.rest.service.utilities.JsonUtils;
import uk.co.kstech.service.PersonService;
import uk.co.kstech.service.exceptions.InvalidUpdateException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by KMcGivern on 7/18/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class RestPersonServiceTest {

    @Configuration
    static class ContextConfiguration {
        @Bean
        public RestPersonService getRestPersonService(){
            return new RestPersonService(getPersonAdapter(), getPersonService());
        }

        @Bean
        public PersonService getPersonService() {
            return Mockito.mock(PersonService.class);
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

    private MockMvc mockMvc;

    @InjectMocks
    @Autowired
    private RestPersonService classUnderTest;

    @Mock
    private PersonService mockPersonService;

    @Mock
    private PersonAdapter personAdapter;

    @Autowired
    private JsonUtils<PersonDTO> jsonUtils;


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).setControllerAdvice(new RestExceptionHandler()).build();
    }

    @Test
    public void shouldCreatePerson() throws Exception {
        PersonDTO dto = DtoBuilder.createPersonDTO();
        final Person person = DtoBuilder.convertPersonDTO(dto);

        when(personAdapter.toPerson(dto)).thenReturn(person);
        when(mockPersonService.createPerson(person)).thenReturn(person);
        when(personAdapter.toPersonDTO(person)).thenReturn(dto);

        String json = jsonUtils.convertToJson(dto);
        this.mockMvc.perform(post("/people").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldUpdatePerson() throws Exception {
        PersonDTO dto = DtoBuilder.createPersonDTO();
        dto.setId(1);
        final Person person = DtoBuilder.convertPersonDTO(dto);
        when(mockPersonService.getPerson(1)).thenReturn(person);
        when(personAdapter.toPerson(dto)).thenReturn(person);
        when(mockPersonService.updatePerson(person, person.getId())).thenReturn(person);
        when(personAdapter.toPersonDTO(person)).thenReturn(dto);

        String json = jsonUtils.convertToJson(dto);
        this.mockMvc.perform(put("/people/1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldRaiseExceptionOnInvalidUpdate() throws Exception {
        PersonDTO dto = DtoBuilder.createPersonDTO();
        dto.setId(1);
        final Person person = DtoBuilder.convertPersonDTO(dto);
        when(personAdapter.toPerson(dto)).thenReturn(person);
        when(mockPersonService.updatePerson(person, 123)).thenThrow(new InvalidUpdateException("invalid update"));

        String json = jsonUtils.convertToJson(dto);
        try{
            this.mockMvc.perform(put("/people/123").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest());
        }catch (InvalidUpdateException ex){
            Assert.assertNotNull(ex);
        }

        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldGetPerson() throws Exception {
        PersonDTO dto = DtoBuilder.createPersonDTO();
        final Person person = DtoBuilder.convertPersonDTO(dto);
        when(personAdapter.toPersonDTO(person)).thenReturn(dto);
        when(mockPersonService.getPerson(1)).thenReturn(person);

        this.mockMvc.perform(get("/people/1")).andExpect(status().isOk());
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldGetAllPeople() throws Exception {
        PersonDTO dto = DtoBuilder.createPersonDTO();
        final Person person = DtoBuilder.convertPersonDTO(dto);
        final List<Person> people = new ArrayList<>();
        final List<PersonDTO> peopleDTOs = new ArrayList<>();
        peopleDTOs.add(dto);
        people.add(person);

        when(personAdapter.toPeopleDTO(people)).thenReturn(peopleDTOs);
        when(mockPersonService.getPeople()).thenReturn(people);

        this.mockMvc.perform(get("/people/all")).andExpect(status().isOk());
        Mockito.validateMockitoUsage();
    }


}
