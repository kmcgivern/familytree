package uk.co.kstech.rest.service.person;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.rest.config.TestRestConfig;
import uk.co.kstech.rest.service.utilities.DtoBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by KMcGivern on 7/18/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRestConfig.class})
public class TestRestPersonService {

    @InjectMocks
    @Autowired
    private PersonService classUnderTest;

    @Mock
    private uk.co.kstech.service.PersonService mockPersonService;

    @Mock
    private PersonAdapter personAdapter;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreatePerson() {
        PersonDTO dto = DtoBuilder.createPersonDTO();
        final Person person = DtoBuilder.convertPersonDTO(dto);
        when(personAdapter.toPerson(dto)).thenReturn(person);
        when(personAdapter.toPersonDTO(person)).thenReturn(dto);
        when(mockPersonService.createPerson(person)).thenReturn(person);

        classUnderTest.createPerson(dto);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldUpdatePerson() {
        PersonDTO dto = DtoBuilder.createPersonDTO();
        dto.setId(1);
        final Person person = DtoBuilder.convertPersonDTO(dto);
        when(personAdapter.toPerson(dto)).thenReturn(person);
        when(personAdapter.toPersonDTO(person)).thenReturn(dto);
        when(mockPersonService.getPerson(1)).thenReturn(person);
        when(mockPersonService.updatePerson(person)).thenReturn(person);

        classUnderTest.updatePerson(dto);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldGetPerson() {
        PersonDTO dto = DtoBuilder.createPersonDTO();
        final Person person = DtoBuilder.convertPersonDTO(dto);
        when(personAdapter.toPersonDTO(person)).thenReturn(dto);
        when(mockPersonService.getPerson(1)).thenReturn(person);

        classUnderTest.createPerson(dto);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldGetAllPeople() {
        PersonDTO dto = DtoBuilder.createPersonDTO();
        final Person person = DtoBuilder.convertPersonDTO(dto);
        final List<Person> people = new ArrayList<>();
        final List<PersonDTO> peopleDTOs = new ArrayList<>();
        peopleDTOs.add(dto);
        people.add(person);

        when(personAdapter.toPeopleDTO(people)).thenReturn(peopleDTOs);
        when(mockPersonService.getPeople()).thenReturn(people);

        classUnderTest.getPeople();
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldDeletePerson() {
        PersonDTO dto = DtoBuilder.createPersonDTO();
        final Person person = DtoBuilder.convertPersonDTO(dto);

        when(mockPersonService.getPerson(1L)).thenReturn(person);
        doNothing().when(mockPersonService).deletePerson(person);

        classUnderTest.deletePerson(1L);
        Mockito.validateMockitoUsage();
    }

    @Test(expected = RestPersonService.PersonNotFoundException.class)
    public void shouldThrowExceptionOnDeletionOfInvalidPerson() {

        when(mockPersonService.getPerson(1L)).thenReturn(null);

        classUnderTest.deletePerson(1L);
        Mockito.validateMockitoUsage();
    }


}
