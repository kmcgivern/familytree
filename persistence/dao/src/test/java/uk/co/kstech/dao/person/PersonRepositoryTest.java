package uk.co.kstech.dao.person;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.co.kstech.dao.TestJpaConfig;
import uk.co.kstech.model.person.Person;

import javax.validation.ConstraintViolationException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertThat;
import static org.springframework.util.Assert.notNull;


/**
 * Created by KMcGivern on 7/17/2014.
 */
@RunWith(SpringRunner.class)
//@SpringBootTest
@ContextConfiguration(classes = {TestJpaConfig.class})
//@Transactional
//@Rollback
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository classUnderTest;

    @Test
    public void databaseIsEmpty() throws Exception {
        long count = classUnderTest.count();
        assertThat(count, IsEqual.equalTo(0L));
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldNotSavePersonWithMissingFields() throws Exception {
        Person person = new Person();
        person.setMiddleName("Chaz");
        person.setMale(true);
        Calendar birthDate =  GregorianCalendar.getInstance();
        birthDate.set(1980, 1, 20);
        person.setBirthDate(birthDate);

        classUnderTest.saveAndFlush(person);
    }

    @Test
    public void shouldSavePerson() throws Exception {
        final Person saved = savePerson();
        notNull(saved.getId());
    }


    @Test
    public void shouldRetrievePerson() {
        //arrange
        final Person saved = savePerson();
        //act
        final Person loadedPerson = classUnderTest.findOne(saved.getId());
        //assert
        notNull(loadedPerson.getId());
    }


    private Person savePerson() {
        final Person saved = classUnderTest.save(createPerson());
        return saved;
    }

    private Person createPerson() {
        Person person = new Person();
        person.setFirstName("Bob");
        person.setMiddleName("Chaz");
        person.setLastName("Davids");
        person.setMale(true);
        Calendar birthDate =  GregorianCalendar.getInstance();
        birthDate.set(1980, 1, 20);
        person.setBirthDate(birthDate);
        return person;
    }

}
