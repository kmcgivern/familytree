package uk.co.kstech.service;

import com.flextrade.jfixture.annotations.Fixture;
import com.flextrade.jfixture.rules.FixtureRule;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.kstech.dao.person.PersonRepository;
import uk.co.kstech.model.person.Person;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PersonServiceTest {


    @Configuration
    public static class Config{

        @Bean
        public PersonService getPersonService(){
            return new PersonServiceImpl(getPersonRepository());
        }

        @Bean
        public PersonRepository getPersonRepository(){
            return Mockito.mock(PersonRepository.class);
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
        when(mockDao.findOne(1L)).thenReturn(person);
        classUnderTest.getPerson(1L);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldGetAllPeople() {
        List<Person> iterable = new ArrayList();
        iterable.add(person);
        when(mockDao.findAll()).thenReturn(iterable);
        final List<Person> people = classUnderTest.getPeople();
        Mockito.validateMockitoUsage();
        Assert.assertThat(people.size(), Matchers.equalTo(1));
    }

    @Test
    public void shouldCreatePerson() {
        when(mockDao.save(person)).thenReturn(person);
        classUnderTest.createPerson(person);
        Mockito.validateMockitoUsage();
    }

    @Test(expected = PersonServiceImpl.PersonConstraintViolationException.class)
    public void shouldFailValidationOnCreate() {
        person.setFirstName(null);
        classUnderTest.createPerson(person);
        Mockito.verifyZeroInteractions(mockDao);
    }

    @Test
    public void shouldUpdatePerson() {
        when(mockDao.save(person)).thenReturn(person);
        classUnderTest.updatePerson(person);
        Mockito.validateMockitoUsage();
    }

    @Test(expected = PersonServiceImpl.PersonConstraintViolationException.class)
    public void shouldFailValidationOnUpdate() {
        person.setFirstName(null);
        classUnderTest.updatePerson(person);
        Mockito.verifyZeroInteractions(mockDao);
    }

    @Test
    public void shouldDeletePerson() {
        doNothing().when(mockDao).delete(person);
        classUnderTest.deletePerson(person);
        Mockito.validateMockitoUsage();
    }


}
