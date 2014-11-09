package uk.co.kstech.rest.service.utilities;

import uk.co.kstech.dto.address.AddressDTO;
import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.address.Address;
import uk.co.kstech.model.person.Person;

import java.util.Date;

/**
 * Created by KMcGivern on 7/18/2014.
 */
public class DtoBuilder {

    public static AddressDTO createAddressDTO() {
        AddressDTO dto = new AddressDTO();
        dto.setFirstLine("1 New Street");
        dto.setSecondLine("");
        dto.setTown("Belfast");
        dto.setPostCode("BT1 1AB");
        return dto;
    }

    public static Address convertAddressDTO(final AddressDTO dto) {
        final Address address = new Address();
        address.setFirstLine(dto.getFirstLine());
        address.setSecondLine(dto.getSecondLine());
        address.setPostCode(dto.getPostCode());
        address.setTown(dto.getTown());
        address.setId(dto.getId());
        return address;
    }


    public static PersonDTO createPersonDTO() {
        PersonDTO dto = new PersonDTO();
        dto.setFirstName("Bob");
        dto.setMiddleName("Chaz");
        dto.setLastName("Davids");
        dto.setBirthDate(new Date());
        dto.getAddresses().add(createAddressDTO());
        return dto;
    }


    public static Person convertPersonDTO(final PersonDTO dto) {
        final Person person = new Person();
        person.setFirstName(dto.getFirstName());
        person.setMiddleName(dto.getMiddleName());
        person.setLastName(dto.getLastName());
        person.setBirthDate(dto.getBirthDate());
        person.getAddresses().add(convertAddressDTO(dto.getAddresses().get(0)));
        person.setId(dto.getId());
        return person;
    }
}
