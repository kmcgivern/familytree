package uk.co.kstech.dao.person;

import static org.junit.Assert.assertThat;
import static org.springframework.util.Assert.notNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
import uk.co.kstech.model.person.Relationship;
import uk.co.kstech.model.person.Relationship.RelationshipType;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestJpaConfig.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RelationshipRepositoryTest {

	  	@Autowired
	    private RelationshipDao classUnderTest;
	  	
	  	@Autowired
	    private PersonDao personDao;

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
	        marriage.setDateOfMarriage(new Date());
	        
	        classUnderTest.save(marriage);
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
	        marriage.setDateOfMarriage(new Date());
	        classUnderTest.save(marriage);
	    }
	    
	    private Person savePerson(Person person) {
	        final Person saved = personDao.save(person);
	        return saved;
	    }
	    
	    private Person createPerson(String first, String middle, String last, boolean isMale) {
	        Person person = new Person();
	        person.setFirstName(first);
	        person.setMiddleName(middle);
	        person.setLastName(last);
	        person.setMale(isMale);
	        person.setBirthDate(LocalDate.of(1980, Month.JANUARY, 20));
	        return person;
	    }
}
