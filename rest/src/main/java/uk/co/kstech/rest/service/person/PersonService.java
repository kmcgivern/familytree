package uk.co.kstech.rest.service.person;

import uk.co.kstech.dto.address.AddressDTO;
import uk.co.kstech.dto.person.PersonDTO;

import java.util.List;

/**
 * Created by KMcGivern on 7/17/2014.
 */
public interface PersonService {

    PersonDTO getPerson(long Id);

    List<PersonDTO> getPeople();

    PersonDTO createPerson(final PersonDTO dto);

    PersonDTO updatePerson(final PersonDTO dto);

    void deletePerson(final long Id);
}
