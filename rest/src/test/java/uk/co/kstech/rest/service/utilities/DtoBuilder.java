package uk.co.kstech.rest.service.utilities;

import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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
        dto.setBirthDate(new Date());
        return dto;
    }


    public static Person convertPersonDTO(final PersonDTO dto) {
        final Person person = new Person(dto.getId());
        person.setFirstName(dto.getFirstName());
        person.setMiddleName(dto.getMiddleName());
        person.setLastName(dto.getLastName());
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(dto.getBirthDate());
        person.setBirthDate(cal);
        return person;
    }
}
