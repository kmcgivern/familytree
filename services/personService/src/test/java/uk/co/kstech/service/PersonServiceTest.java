package uk.co.kstech.service;

import com.flextrade.jfixture.annotations.Fixture;
import com.flextrade.jfixture.rules.FixtureRule;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.kstech.dao.person.PersonRepository;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.service.exceptions.InvalidUpdateException;
import uk.co.kstech.service.exceptions.PersonConstraintViolationException;
import uk.co.kstech.service.exceptions.PersonNotFoundException;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PersonServiceTest {


    @Configuration
    public static class Config {

        @Bean
        public PersonService getPersonService() {
            return new PersonServiceImpl(getPersonRepository());
        }

        @Bean
        public PersonRepository getPersonRepository() {
            return mock(PersonRepository.class);
        }
    }

    @Rule
    public FixtureRule fr = FixtureRule.initFixtures();

    @Autowired
    private PersonService classUnderTest;

    @Fixture
    private Person person;

    @Autowired
    private PersonRepository mockDao;

    @Before
    public void setUp() {
        reset(mockDao);
    }

    @Test
    public void shouldGetPerson() {
        when(mockDao.findOne(anyLong())).thenReturn(person);
        //act
        classUnderTest.getPerson(1L);
        validateMockitoUsage();
    }

    @Test
    public void shoulRaiseExceptionWhenPersonDoesNotExist() {
        when(mockDao.findOne(1L)).thenReturn(null);
        //act
        try {
            //act
            classUnderTest.getPerson(1L);
        } catch (PersonNotFoundException ex) {
            assertNotNull(ex);
        }

        validateMockitoUsage();
    }

    @Test
    public void shouldGetAllPeople() {
        List<Person> iterable = new ArrayList();
        iterable.add(person);
        when(mockDao.findAll()).thenReturn(iterable);
        //act
        final List<Person> people = classUnderTest.getPeople();
        validateMockitoUsage();
        assertThat(people.size(), Matchers.equalTo(1));
    }

    @Test
    public void shouldCreatePerson() {
        when(mockDao.save(person)).thenReturn(person);
        //act
        classUnderTest.createPerson(person);
        validateMockitoUsage();
    }

    @Test(expected = PersonConstraintViolationException.class)
    public void shouldFailValidationOnCreate() {
        person.setFirstName(null);
        //act
        classUnderTest.createPerson(person);
        verifyZeroInteractions(mockDao);
    }

    @Test
    public void shouldUpdatePerson() {
        Person existing = getPerson(1L);
        Person update = getPerson(1L);
        when(mockDao.findOne(existing.getId())).thenReturn(existing);
        when(mockDao.save(update)).thenReturn(update);
        //act
        classUnderTest.updatePerson(update, existing.getId());
        verify(mockDao, times(1)).save(any(Person.class));
    }

    @Test
    public void shouldNotUpdatePersonIfIdsDiffer() {
        Person existing = getPerson(2L);
        Person update = getPerson(1L);
        when(mockDao.findOne(existing.getId())).thenReturn(existing);

        //act
        try {
            //act
            classUnderTest.updatePerson(update, existing.getId());
        } catch (InvalidUpdateException ex) {
            assertNotNull(ex);
        }

        verify(mockDao, never()).save(any(Person.class));
    }

    @Test(expected = PersonConstraintViolationException.class)
    public void shouldFailValidationOnUpdate() {
        Person existing = getPerson(1L);
        when(mockDao.findOne(existing.getId())).thenReturn(existing);
        existing.setFirstName(null);
        //act
        classUnderTest.updatePerson(existing, existing.getId());
        verifyZeroInteractions(mockDao);
    }

    @Test
    public void shouldDeletePerson() {
        when(mockDao.findOne(anyLong())).thenReturn(person);
        doNothing().when(mockDao).delete(anyLong());
        //act
        classUnderTest.deletePerson(1L);
        validateMockitoUsage();
    }

    @Test
    public void shouldThrowExceptionIfPersonDoesNotExistOnDelete() {
        when(mockDao.findOne(anyLong())).thenReturn(null);
        try {
            //act
            classUnderTest.deletePerson(1L);
        } catch (PersonNotFoundException ex) {
            assertNotNull(ex);
        }
        verify(mockDao, never()).delete(anyLong());
    }

    private Person getPerson(long id) {
        Person p = new Person(id);
        p.setFirstName("bob");
        p.setLastName("chaz");
        p.setMale(true);
        p.setBirthDate(GregorianCalendar.getInstance());
        return p;
    }

}
