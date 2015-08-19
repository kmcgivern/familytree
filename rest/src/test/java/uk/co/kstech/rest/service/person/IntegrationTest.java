package uk.co.kstech.rest.service.person;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.rest.config.TestRestConfig;
import uk.co.kstech.rest.service.utilities.DtoBuilder;
import uk.co.kstech.rest.service.utilities.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by KMcGivern on 7/18/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRestConfig.class})
public class IntegrationTest {


    private MockMvc mockMvc;

    @InjectMocks
    @Autowired
    private PersonService classUnderTest;

    @Mock
    private uk.co.kstech.service.PersonService mockPersonService;

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
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build();
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
        person.setVersion(1L);
        when(mockPersonService.getPerson(1)).thenReturn(person);
        when(personAdapter.toPerson(dto)).thenReturn(person);
        when(mockPersonService.updatePerson(person)).thenReturn(person);
        when(personAdapter.toPersonDTO(person)).thenReturn(dto);

        String json = jsonUtils.convertToJson(dto);
        this.mockMvc.perform(put("/people").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
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
