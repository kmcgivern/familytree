package uk.co.kstech.rest.service.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.service.PersonService;

import java.util.List;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@RestController
@RequestMapping("/people")
public class RestPersonService  {

    private PersonAdapter personAdapter;

    private PersonService personService;

    @Autowired
    public RestPersonService(PersonAdapter personAdapter, PersonService personService){
        this.personAdapter = personAdapter;
        this.personService = personService;
    }

    @RequestMapping(value = "/{personId}", method = RequestMethod.GET, produces = "application/json")
    public PersonDTO getPerson(@PathVariable long personId) {
        final Person person = personService.getPerson(personId);
        PersonDTO dto = personAdapter.toPersonDTO(person);
        return dto;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = "application/json")
    public List<PersonDTO> getPeople() {
        final List<Person> people = personService.getPeople();
        return personAdapter.toPeopleDTO(people);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public PersonDTO createPerson(@RequestBody(required = true)PersonDTO dto) {
        final Person person = personAdapter.toPerson(dto);
        personService.createPerson(person);
        return personAdapter.toPersonDTO(person);
    }

    @RequestMapping(value = "/{personId}",method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public PersonDTO updatePerson(@PathVariable long personId, @RequestBody(required = true)PersonDTO dto) {
        final Person updated = personAdapter.toPerson(dto);
        personService.updatePerson(updated, personId);
        return personAdapter.toPersonDTO(updated);
    }

    @RequestMapping(value = "/{personId}", method = RequestMethod.DELETE)
    public void deletePerson(@PathVariable long personId) {
        personService.deletePerson(personId);
    }

    private boolean personFound(final Person person) {
        return person != null;
    }

}
