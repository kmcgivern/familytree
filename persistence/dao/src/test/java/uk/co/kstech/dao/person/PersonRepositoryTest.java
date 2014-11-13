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

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.Month;

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
    public void shouldSaveRelationship() throws Exception {
        final Person child = classUnderTest.save(createChild());
        final Person father = classUnderTest.save(createFather(child));
        Assert.notNull(child.getId());
        Assert.notNull(father.getId());
        Assert.notNull(father.getChildren().iterator().next().getId());
    }

    @Test
    public void shouldRetrievePerson() {
        final Person saved = savePerson();
        final Person loadedPerson = classUnderTest.findOne(saved.getId());
        Assert.notNull(loadedPerson.getId());
    }


    private Person savePerson() {
        final Person saved = classUnderTest.save(createFather());
        return saved;
    }

    private Person createFather() {
        Person Person = new Person();
        Person.setFirstName("Bob");
        Person.setMiddleName("Chaz");
        Person.setLastName("Davids");
        Person.setMale(true);
        Person.setBirthDate(LocalDate.of(1980, Month.JANUARY, 20));
        Person.addChild(createChild());
        return Person;
    }

    private Person createFather(Person child) {
        Person Person = new Person();
        Person.setFirstName("Bob");
        Person.setMiddleName("Chaz");
        Person.setLastName("Davids");
        Person.setMale(true);
        Person.setBirthDate(LocalDate.of(1980, Month.JANUARY, 20));
        Person.addChild(child);
        return Person;
    }

    private Person createChild() {

        Person child = new Person();
        child.setFirstName("Dave");
        child.setMiddleName("Chaz");
        child.setLastName("Davids");
        child.setMale(true);
        child.setBirthDate(LocalDate.of(2000, Month.NOVEMBER, 20));
        return child;
    }

}
