package uk.co.kstech.adapter;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.adapter.person.PersonAdapterImpl;
import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.service.PersonService;

import java.util.*;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PersonAdapterTest {

    @Configuration
    public static class Config {

        @Bean
        public PersonAdapter getPersonAdapter() {
            return new PersonAdapterImpl(getPersonService());
        }

        @Bean
        public PersonService getPersonService() {
            return Mockito.mock(PersonService.class);
        }
    }

    @Autowired
    private PersonAdapter classUnderTest;

    @Autowired
    private PersonService mockPersonService;

    @Before
    public void initMocks() {
        reset(mockPersonService);
    }

    @Test
    public void shouldCopyPersonToDTO() {
        Person model = createPerson();

        final PersonDTO dto = classUnderTest.toPersonDTO(model);

        Mockito.verifyZeroInteractions(mockPersonService);

        verifyFields(dto, model);
        Assert.assertEquals(model.getId(), new Long(dto.getId()));
    }

    @Test
    public void shouldCopyPersonListToDTO() {
        Person model = createPerson();
        final List<Person> personList = new ArrayList<>();
        personList.add(model);

        final List<PersonDTO> dtoList = classUnderTest.toPeopleDTO(personList);
        Mockito.verifyZeroInteractions(mockPersonService);

        verifyFields(dtoList.get(0), model);
        Assert.assertEquals(model.getId(), new Long(dtoList.get(0).getId()));
    }

    @Test
    public void shouldCopyDTOToPerson() {
        PersonDTO dto = createPersonDTO();
        dto.setId(0L);
        when(mockPersonService.getPerson(dto.getId())).thenReturn(null);
        final Person model = classUnderTest.toPerson(dto);
        Mockito.validateMockitoUsage();
        verifyFields(dto, model);
        Assert.assertEquals(null, model.getId());
    }

    @Test
    public void shouldCopyPersonDTOListToPerson() {
        PersonDTO dto = createPersonDTO();
        dto.setId(0L);
        when(mockPersonService.getPerson(dto.getId())).thenReturn(null);

        final List<PersonDTO> personDTOList = new ArrayList<>();
        personDTOList.add(dto);

        final List<Person> personList = classUnderTest.toPeople(personDTOList);
        Mockito.validateMockitoUsage();
        verifyFields(dto, personList.get(0));
        Assert.assertEquals(null, personList.get(0).getId());
    }

    @Test
    public void shouldUpdatePerson() {
        PersonDTO dto = createPersonDTO();
        dto.setFirstName("modified");

        Person originalFromDB = createPerson();

        when(mockPersonService.getPerson(dto.getId())).thenReturn(originalFromDB);
        final Person model = classUnderTest.toPerson(dto);
        verifyFields(dto, model);
    }

    private void verifyFields(final PersonDTO dto, final Person model) {
        Assert.assertThat(model.getFirstName(), Matchers.equalToIgnoringWhiteSpace(dto.getFirstName()));
        Assert.assertThat(model.getMiddleName(), Matchers.equalToIgnoringWhiteSpace(dto.getMiddleName()));
        Assert.assertThat(model.getLastName(), Matchers.equalToIgnoringWhiteSpace(dto.getLastName()));
    }

    private Person createPerson() {
        Person person = new Person(1L);
        person.setFirstName("Bob");
        person.setMiddleName("Chaz");
        person.setLastName("Davids");
        Calendar cal = GregorianCalendar.getInstance();
        person.setBirthDate(cal);
        return person;
    }


    private PersonDTO createPersonDTO() {
        PersonDTO dto = new PersonDTO();
        dto.setFirstName("Bob");
        dto.setMiddleName("Chaz");
        dto.setLastName("Davids");
        dto.setBirthDate(new Date());
        return dto;
    }
}
