package uk.co.kstech.rest.service.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.service.*;

import java.util.List;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@RestController
@RequestMapping("/people")
public class RestPersonService implements PersonService{

    @Autowired
    private PersonAdapter personAdapter;

    @Autowired
    private uk.co.kstech.service.PersonService personService;

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public PersonDTO getPerson(@RequestParam(value = "Id", required = true) long Id) {
        final Person person = personService.getPerson(Id);
        PersonDTO dto = null;
        if (personFound(person)) {
            dto = personAdapter.toPersonDTO(person);
        } else {
            throw new PersonNotFoundException("Could not find Person for the given Person ID:" + Id);
        }
        return dto;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = "application/json")
    public List<PersonDTO> getPeople() {
        final List<Person> people = personService.getPeople();
        return personAdapter.toPeopleDTO(people);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public PersonDTO createPerson(@RequestBody(required = true)PersonDTO dto) {
        final Person person = personAdapter.toPerson(dto);
        personService.createPerson(person);
        return personAdapter.toPersonDTO(person);
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public PersonDTO updatePerson(@RequestBody(required = true)PersonDTO dto) {
        final Person addressFromDb = personService.getPerson(dto.getId());
        final Person updated = personAdapter.toPerson(dto);
        updated.setVersion(addressFromDb.getVersion());
        personService.updatePerson(updated);
        return personAdapter.toPersonDTO(updated);
    }

    @Override
    @RequestMapping(method = RequestMethod.DELETE)
    public void deletePerson(@RequestBody(required = true)long Id) {
        final Person person = personService.getPerson(Id);
        if (personFound(person)) {
            personService.deletePerson(person);
        } else {
            throw new PersonNotFoundException("Could not find Person for the given Person ID:" + Id);
        }
    }

    private boolean personFound(final Person person) {
        return person != null;
    }

    public class PersonNotFoundException extends RuntimeException {

        public PersonNotFoundException() {
        }

        public PersonNotFoundException(final String error) {
            super(error);
        }

    }
}
