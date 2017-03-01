package uk.co.kstech.dao.person;

import org.hamcrest.core.IsEqual;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.co.kstech.dao.TestJpaConfig;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.model.person.Relationship;
import uk.co.kstech.model.person.Relationship.RelationshipType;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertThat;
import static org.springframework.util.Assert.notNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestJpaConfig.class})
@Transactional
@Rollback
public class RelationshipRepositoryTest {

    @Autowired
    private RelationshipRepository classUnderTest;

    @Autowired
    private PersonRepository personRepository;

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
    public void shouldSaveRelationship() throws Exception {
        Person man = createPerson("Chaz", "Michael", "Michaels", true);
        savePerson(man);
        notNull(man.getId());
        Person woman = createPerson("Betty", "Anne", "Michaels", true);
        savePerson(woman);
        notNull(woman.getId());

        Relationship marriage = new Relationship();
        marriage.setPersonID1(man.getId());
        marriage.setPersonID2(woman.getId());
        marriage.setRelationshipType(RelationshipType.MARRIED);
        marriage.setDateOfMarriage(GregorianCalendar.getInstance());

        classUnderTest.saveAndFlush(marriage);
        notNull(marriage.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldNotSaveRelationshipWithOnlyOnePerson() throws Exception {
        Person man = createPerson("Chaz", "Michael", "Michaels", true);
        savePerson(man);
        notNull(man.getId());

        Relationship marriage = new Relationship();
        marriage.setPersonID1(man.getId());
        marriage.setRelationshipType(RelationshipType.MARRIED);
        marriage.setDateOfMarriage(GregorianCalendar.getInstance());
        classUnderTest.saveAndFlush(marriage);
    }

    private Person savePerson(Person person) {
        final Person saved = personRepository.saveAndFlush(person);
        return saved;
    }

    private Person createPerson(String first, String middle, String last, boolean isMale) {
        Person person = new Person();
        person.setFirstName(first);
        person.setMiddleName(middle);
        person.setLastName(last);
        person.setMale(isMale);
        Calendar birthDate =  GregorianCalendar.getInstance();
        birthDate.set(1980, 1, 20);
        person.setBirthDate(birthDate);
        return person;
    }
}
