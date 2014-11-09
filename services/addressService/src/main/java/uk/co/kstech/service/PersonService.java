package uk.co.kstech.service;

import uk.co.kstech.model.address.Address;
import uk.co.kstech.model.person.Person;

import java.util.List;

/**
 * Created by KMcGivern on 7/17/2014.
 */
public interface PersonService {

    Person getPerson(final long id);

    List<Person> getPeople();

    Person createPerson(final Person person);

    Person updatePerson(final Person person);

    void deletePerson(final Person person);
}
