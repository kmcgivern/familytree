package uk.co.kstech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.kstech.dao.person.PersonRepository;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.service.message.ConstraintError;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@Service(value ="PersonServiceImpl" )
public class PersonServiceImpl implements PersonService{

    private static Validator validator;

    @PostConstruct
    public static void setUpValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @Override
    public Person getPerson(long id) {
            return personRepository.findOne(id);
    }

    @Override
    public List<Person> getPeople() {
        return makeList(personRepository.findAll());
    }

    @Override
    public Person createPerson(Person person) {
        validate(person);
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Person person) {
        validate(person);
        return personRepository.save(person);
    }

    @Override
    public void deletePerson(Person person) {
        personRepository.delete(person);
    }

    private void validate(final Person person) {
        final Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
        if (!constraintViolations.isEmpty()) {
            throw new PersonConstraintViolationException(formatViolations(constraintViolations));
        }
    }

    private <E> List<E> makeList(final Iterable<E> iter) {
        final List<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }

    private String formatViolations(final Set<ConstraintViolation<Person>> constraintViolations) {
        StringBuilder errors = new StringBuilder();
        errors.append("Person Constraint violation. ");
        for (ConstraintViolation<Person> cv : constraintViolations) {
            ConstraintError ce = new ConstraintError(cv.getPropertyPath().toString(), cv.getInvalidValue(), cv.getMessage());
            errors.append(ce.toString());
        }
        return errors.toString();
    }

    class PersonConstraintViolationException extends RuntimeException {

        public PersonConstraintViolationException(final String message) {
            super(message);
        }

    }
}
