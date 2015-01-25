package uk.co.kstech.service;

import org.hamcrest.Matchers;
import org.junit.Assert;
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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import uk.co.kstech.dao.person.PersonDao;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.service.config.TestServiceConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestServiceConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PersonServiceTest {

    @InjectMocks
    @Autowired
    private PersonService classUnderTest;

    @Mock
    private PersonDao mockDao;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetPerson() {
        when(mockDao.findOne(1L)).thenReturn(createPerson());
        classUnderTest.getPerson(1L);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldGetAllPeople() {
        Iterable<Person> iterable = new ArrayList();
        ((ArrayList) iterable).add(createPerson());
        when(mockDao.findAll()).thenReturn(iterable);
        final List<Person> people = classUnderTest.getPeople();
        Mockito.validateMockitoUsage();
        Assert.assertThat(people.size(), Matchers.equalTo(1));
    }

    @Test
    public void shouldCreatePerson() {
        Person person = createPerson();
        when(mockDao.save(person)).thenReturn(person);
        classUnderTest.createPerson(person);
        Mockito.validateMockitoUsage();
    }

    @Test(expected = PersonServiceImpl.PersonConstraintViolationException.class)
    public void shouldFailValidationOnCreate() {
        Person person = createPerson();
        person.setFirstName(null);
        classUnderTest.createPerson(person);
        Mockito.verifyZeroInteractions(mockDao);
    }

    @Test
    public void shouldUpdatePerson() {
        Person person = createPerson();
        when(mockDao.save(person)).thenReturn(person);
        classUnderTest.updatePerson(person);
        Mockito.validateMockitoUsage();
    }

    @Test(expected = PersonServiceImpl.PersonConstraintViolationException.class)
    public void shouldFailValidationOnUpdate() {
        Person person = createPerson();
        person.setFirstName(null);
        classUnderTest.updatePerson(person);
        Mockito.verifyZeroInteractions(mockDao);
    }

    @Test
    public void shouldDeletePerson() {
        Person person = createPerson();
        doNothing().when(mockDao).delete(person);
        classUnderTest.deletePerson(person);
        Mockito.validateMockitoUsage();
    }

    private Person createPerson() {
        Person person = new Person();
        person.setFirstName("Bob");
        person.setMiddleName("Chaz");
        person.setLastName("Davids");
        person.setBirthDate( LocalDate.now());
        return person;
    }


}
