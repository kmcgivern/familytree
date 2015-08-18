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
    public void shouldSaveRelationship() throws Exception {
        final Person child = classUnderTest.save(createChild());
        final Person father = classUnderTest.save(createFather(child));
        notNull(child.getId());
        notNull(father.getId());
        notNull(father.getChildren().iterator().next().getId());
        assertThat(father.getChildren().iterator().next().getId(), IsEqual.equalTo(child.getId()));
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

    @Test
    public void shouldGetAllChildrenForParent(){
        final Person father = classUnderTest.save(createFather());
        father.addChild(createChild("John", "Peter", father.getLastName(), LocalDate.of(2000, Month.NOVEMBER, 20), true));
        father.addChild(createChild("James", "Paul", father.getLastName(), LocalDate.of(2002, Month.JANUARY, 15), true));
        classUnderTest.save(father);
        assertThat(father.getChildren().size(), IsEqual.equalTo(2));
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

    private Person createChild(final String first, final String middle, final String last, final LocalDate dob, final boolean isMale) {

        Person child = new Person();
        child.setFirstName(first);
        child.setMiddleName(middle);
        child.setLastName(last);
        child.setMale(isMale);
        child.setBirthDate(dob);
        return child;
    }

}
