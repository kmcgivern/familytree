package uk.co.kstech.adapter.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.kstech.dto.person.DateRepresentation;
import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.service.PersonService;

import java.util.*;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@Component
public class PersonAdapterImpl implements PersonAdapter {

    private PersonService personService;

    @Autowired
    public PersonAdapterImpl(PersonService personService){
        this.personService = personService;
    }

    @Override
    public List<Person> toPeople(List<PersonDTO> list) {
        final List<Person> personList = new ArrayList<>(list.size());
        for (PersonDTO dto : list) {
            personList.add(toPerson(dto));
        }
        return personList;
    }

    @Override
    public Person toPerson(PersonDTO dto) {
        final Person person = getPerson(dto);
        person.setFirstName(dto.getFirstName());
        person.setMiddleName(dto.getMiddleName());
        person.setLastName(dto.getLastName());
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(dto.getBirthDate().getYear(), dto.getBirthDate().getMonth(), dto.getBirthDate().getDay());
        person.setBirthDate(cal);
        return person;
    }

    @Override
    public PersonDTO toPersonDTO(Person model) {
        final PersonDTO dto = new PersonDTO();
        dto.setFirstName(model.getFirstName());
        dto.setMiddleName(model.getMiddleName());
        dto.setLastName(model.getLastName());
        DateRepresentation dr = new DateRepresentation();
        dr.setYear(model.getBirthDate().get(Calendar.YEAR));
        dr.setMonth(model.getBirthDate().get(Calendar.MONTH));
        dr.setDay(model.getBirthDate().get(Calendar.DAY_OF_MONTH));
        dto.setBirthDate(dr);
        dto.setId(model.getId());
        return dto;
    }

    @Override
    public List<PersonDTO> toPeopleDTO(List<Person> list) {
        final List<PersonDTO> addressDtoList = new ArrayList<>(list.size());
        for (Person person : list) {
            addressDtoList.add(toPersonDTO(person));
        }
        return addressDtoList;
    }

    private Person getPerson(final PersonDTO dto) {
        Person person = personService.getPerson(dto.getId());
        if (person == null) {
            person = new Person();
        }
        return person;
    }
}
