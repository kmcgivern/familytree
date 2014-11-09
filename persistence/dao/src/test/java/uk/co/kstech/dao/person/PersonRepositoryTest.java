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
import org.springframework.util.Assert;
import uk.co.kstech.dao.TestJpaConfig;
import uk.co.kstech.model.person.Person;

import javax.validation.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static org.junit.Assert.assertThat;

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
        Assert.notNull(saved.getId());
    }

    @Test
    public void shouldRetrievePerson() {
        final Person saved = classUnderTest.save(savePerson());
        final Person loadedPerson = classUnderTest.findOne(saved.getId());
        Assert.notNull(loadedPerson.getId());
        Assert.notNull(loadedPerson.getChildren().iterator().next().getId());
    }

    private void validate(final Person person, int expectedViolations) {
        Set<ConstraintViolation<Person>> constraintViolations = validator.validate( person );
        assertThat(constraintViolations.size(), IsEqual.equalTo(expectedViolations));
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
        person.addChild(createChild());
        return person;
    }

    private Person createChild() {
        Person person = new Person();
        person.setFirstName("Dave");
        person.setMiddleName("Chaz");
        person.setLastName("Davids");
        person.setMale(true);
        person.setBirthDate(LocalDate.of(2000, Month.NOVEMBER, 20));
        return person;
    }
}
