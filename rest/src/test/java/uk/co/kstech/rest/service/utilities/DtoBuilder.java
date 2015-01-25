package uk.co.kstech.rest.service.utilities;

import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;

import java.time.ZoneId;
import java.util.Date;

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
        final Person person = new Person();
        person.setFirstName(dto.getFirstName());
        person.setMiddleName(dto.getMiddleName());
        person.setLastName(dto.getLastName());
        person.setBirthDate(dto.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        person.setId(dto.getId());
        return person;
    }
}
