package uk.co.kstech.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import autofixture.publicinterface.Fixture;
import uk.co.kstech.dao.person.PersonDao;
import uk.co.kstech.model.person.Person;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@RunWith(MockitoJUnitRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PersonServiceTest {

    @InjectMocks
    private PersonService classUnderTest = new PersonServiceImpl();

    @Mock
    private PersonDao mockDao;

    @Before
    public void initMocks() {
    	PersonServiceImpl.setUpValidation();
    }

    @Test
    public void shouldGetPerson() {
    	Fixture fixture = new Fixture();
        Person person = fixture.create(Person.class);
        when(mockDao.findOne(1L)).thenReturn(person);
        classUnderTest.getPerson(1L);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldGetAllPeople() {
    	Fixture fixture = new Fixture();
        Person person = fixture.create(Person.class);
        Iterable<Person> iterable = new ArrayList();
        ((ArrayList) iterable).add(person);
        when(mockDao.findAll()).thenReturn(iterable);
        final List<Person> people = classUnderTest.getPeople();
        Mockito.validateMockitoUsage();
        Assert.assertThat(people.size(), Matchers.equalTo(1));
    }

    @Test
    public void shouldCreatePerson() {
    	Fixture fixture = new Fixture();
        Person person = fixture.create(Person.class);
        when(mockDao.save(person)).thenReturn(person);
        classUnderTest.createPerson(person);
        Mockito.validateMockitoUsage();
    }

    @Test(expected = PersonServiceImpl.PersonConstraintViolationException.class)
    public void shouldFailValidationOnCreate() {
    	Fixture fixture = new Fixture();
        Person person = fixture.create(Person.class);
        person.setFirstName(null);
        classUnderTest.createPerson(person);
        Mockito.verifyZeroInteractions(mockDao);
    }

    @Test
    public void shouldUpdatePerson() {
    	Fixture fixture = new Fixture();
        Person person = fixture.create(Person.class);
        when(mockDao.save(person)).thenReturn(person);
        classUnderTest.updatePerson(person);
        Mockito.validateMockitoUsage();
    }

    @Test(expected = PersonServiceImpl.PersonConstraintViolationException.class)
    public void shouldFailValidationOnUpdate() {
    	Fixture fixture = new Fixture();
        Person person = fixture.create(Person.class);
        person.setFirstName(null);
        classUnderTest.updatePerson(person);
        Mockito.verifyZeroInteractions(mockDao);
    }

    @Test
    public void shouldDeletePerson() {
    	Fixture fixture = new Fixture();
        Person person = fixture.create(Person.class);
        doNothing().when(mockDao).delete(person);
        classUnderTest.deletePerson(person);
        Mockito.validateMockitoUsage();
    }


}
