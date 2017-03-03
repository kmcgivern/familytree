package uk.co.kstech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.kstech.dao.person.PersonRepository;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.service.exceptions.InvalidUpdateException;
import uk.co.kstech.service.exceptions.PersonConstraintViolationException;
import uk.co.kstech.service.exceptions.PersonNotFoundException;
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
@Service(value = "PersonServiceImpl")
public class PersonServiceImpl implements PersonService {

    private static Validator validator;

    @PostConstruct
    public static void setUpValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person getPerson(long id) {
        Person found = personRepository.findOne(id);
        if (found == null) {
            throw new PersonNotFoundException("Could not find Person for the given ID:" + id);
        }
        return found;
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
    public Person updatePerson(final Person personToUpdate, final long id) {
        Person inDb = getPerson(id);
        if(! inDb.getId().equals(personToUpdate.getId())){
            throw new InvalidUpdateException(String.format("The Id as the parameter '%s' does match the ID in the person being updated '%s'", id, personToUpdate.getId()) );
        }
        validate(personToUpdate);
        return personRepository.save(personToUpdate);
    }

    @Override
    public void deletePerson(final long id) {
        Person found = personRepository.findOne(id);
        if (found == null) {
            throw new PersonNotFoundException(String.format("Could not find Person for the given ID: %s", id) );
        }
        personRepository.delete(id);
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

}
