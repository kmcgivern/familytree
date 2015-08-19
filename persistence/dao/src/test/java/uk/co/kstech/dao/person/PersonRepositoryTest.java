package uk.co.kstech.dao.person;

import org.hamcrest.core.IsEqual;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import uk.co.kstech.dao.TestJpaConfig;
import uk.co.kstech.model.person.Person;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertThat;
import static org.springframework.util.Assert.notNull;


/**
 * Created by KMcGivern on 7/17/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestJpaConfig.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PersonRepositoryTest {

    @Autowired
    private PersonDao classUnderTest;

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void databaseIsEmpty() throws Exception {
        long count = classUnderTest.count();
        assertThat(count, IsEqual.equalTo(0L));
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
        person.setBirthDate(LocalDate.of(1980, Month.JANUARY, 20));
        return person;
    }

}
