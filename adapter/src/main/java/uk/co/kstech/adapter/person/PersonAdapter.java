package uk.co.kstech.adapter.person;

import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;

import java.util.List;

/**
 * Created by KMcGivern on 7/17/2014.
 */
public interface PersonAdapter {

    public List<Person> toPeople(final List<PersonDTO> list);

    public Person toPerson(final PersonDTO dto);

    public PersonDTO toPersonDTO(final Person model);

    public List<PersonDTO> toPeopleDTO(final List<Person> list);
}
