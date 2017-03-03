package uk.co.kstech.service;

import uk.co.kstech.model.person.Person;

import java.util.List;

/**
 * Created by KMcGivern on 7/17/2014.
 */
public interface PersonService {

    Person getPerson(final long id);

    List<Person> getPeople();

    Person createPerson(final Person person);

    Person updatePerson(final Person personToUpdate, final long id);

    void deletePerson(final long id);
}
