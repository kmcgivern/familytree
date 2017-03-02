package uk.co.kstech.rest.service.utilities;

import uk.co.kstech.dto.person.DateRepresentation;
import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by KMcGivern on 7/18/2014.
 */
public class DtoBuilder {

    public static PersonDTO createPersonDTO() {
        PersonDTO dto = new PersonDTO();
        dto.setFirstName("Bob");
        dto.setMiddleName("Chaz");
        dto.setLastName("Davids");
        DateRepresentation dr = new DateRepresentation();
        dr.setYear(1984);
        dr.setMonth(2);
        dr.setDay(12);

        dto.setBirthDate(dr);
        return dto;
    }


    public static Person convertPersonDTO(final PersonDTO dto) {
        final Person person = new Person(dto.getId());
        person.setFirstName(dto.getFirstName());
        person.setMiddleName(dto.getMiddleName());
        person.setLastName(dto.getLastName());
        Calendar cal = GregorianCalendar.getInstance();
        cal.set(dto.getBirthDate().getYear(), dto.getBirthDate().getMonth(), dto.getBirthDate().getDay());
        person.setBirthDate(cal);
        return person;
    }
}
