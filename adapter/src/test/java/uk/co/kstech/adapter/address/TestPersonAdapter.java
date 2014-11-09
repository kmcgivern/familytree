package uk.co.kstech.adapter.address;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.dto.address.AddressDTO;
import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.address.Address;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.service.AddressService;
import uk.co.kstech.service.PersonService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAdapterConfig.class})
public class TestPersonAdapter {

    @InjectMocks
    @Autowired
    private PersonAdapter classUnderTest;

    @Mock
    private PersonService mockPersonService;

    @Mock
    private AddressAdapter mockAddressAdapter;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCopyPersonToDTO() {
        Person model = createPerson();
        when(mockAddressAdapter.toAddressDTO(model.getAddresses())).thenReturn(Arrays.asList(createAddressDTO()));

        final PersonDTO dto = classUnderTest.toPersonDTO(model);

        Mockito.verifyZeroInteractions(mockPersonService);

        verifyFields(dto, model);
        Assert.assertEquals(model.getId(), new Long(dto.getId()));
    }

    @Test
    public void shouldCopyPersonListToDTO() {
        Person model = createPerson();
        final List<Person> personList = new ArrayList<>();
        personList.add(model);

        when(mockAddressAdapter.toAddressDTO(model.getAddresses())).thenReturn(Arrays.asList(createAddressDTO()));
        final List<PersonDTO> dtoList = classUnderTest.toPeopleDTO(personList);
        Mockito.verifyZeroInteractions(mockPersonService);

        verifyFields(dtoList.get(0), model);
        Assert.assertEquals(model.getId(), new Long(dtoList.get(0).getId()));
    }

    @Test
    public void shouldCopyDTOToPerson() {
        PersonDTO dto = createPersonDTO();
        dto.setId(0L);
        when(mockPersonService.getPerson(dto.getId())).thenReturn(null);
        when(mockAddressAdapter.toAddress(dto.getAddresses())).thenReturn(Arrays.asList(createAddress()));
        final Person model = classUnderTest.toPerson(dto);
        Mockito.validateMockitoUsage();
        verifyFields(dto, model);
        Assert.assertEquals(null, model.getId());
    }

    @Test
    public void shouldCopyPersonDTOListToPerson() {
        PersonDTO dto = createPersonDTO();
        dto.setId(0L);
        when(mockPersonService.getPerson(dto.getId())).thenReturn(null);
        when(mockAddressAdapter.toAddress(dto.getAddresses())).thenReturn(Arrays.asList(createAddress()));

        final List<PersonDTO> personDTOList = new ArrayList<>();
        personDTOList.add(dto);

        final List<Person> personList = classUnderTest.toPeople(personDTOList);
        Mockito.validateMockitoUsage();
        verifyFields(dto, personList.get(0));
        Assert.assertEquals(null, personList.get(0).getId());
    }

    @Test
    public void shouldUpdatePerson() {
        PersonDTO dto = createPersonDTO();
        dto.setFirstName("modified");

        Person originalFromDB = createPerson();

        when(mockAddressAdapter.toAddress(dto.getAddresses())).thenReturn(Arrays.asList(createAddress()));
        when(mockPersonService.getPerson(dto.getId())).thenReturn(originalFromDB);
        final Person model = classUnderTest.toPerson(dto);
        verifyFields(dto, model);
    }

    private void verifyFields(final PersonDTO dto, final Person model) {
        Assert.assertThat(model.getFirstName(), Matchers.equalToIgnoringWhiteSpace(dto.getFirstName()));
        Assert.assertThat(model.getMiddleName(), Matchers.equalToIgnoringWhiteSpace(dto.getMiddleName()));
        Assert.assertThat(model.getLastName(), Matchers.equalToIgnoringWhiteSpace(dto.getLastName()));
        Assert.assertThat(model.getBirthDate(), Matchers.equalTo(dto.getBirthDate()));

        Assert.assertThat(model.getAddresses().get(0).getFirstLine(), Matchers.equalToIgnoringWhiteSpace(dto.getAddresses().get(0).getFirstLine()));
    }

    private Address createAddress() {
        Address address = new Address();
        address.setFirstLine("1 New Street");
        address.setSecondLine("");
        address.setTown("Belfast");
        address.setPostCode("BT1 1AB");
        address.setId(1L);

        return address;
    }

    private Person createPerson() {
        Person person = new Person();
        person.setFirstName("Bob");
        person.setMiddleName("Chaz");
        person.setLastName("Davids");
        person.setBirthDate(new Date());
        person.getAddresses().add(createAddress());
        person.setId(1L);
        return person;
    }

    private AddressDTO createAddressDTO() {
        AddressDTO dto = new AddressDTO();
        dto.setFirstLine("1 New Street");
        dto.setSecondLine("");
        dto.setTown("Belfast");
        dto.setPostCode("BT1 1AB");
        dto.setId(1L);

        return dto;
    }

    private PersonDTO createPersonDTO() {
        PersonDTO dto = new PersonDTO();
        dto.setFirstName("Bob");
        dto.setMiddleName("Chaz");
        dto.setLastName("Davids");
        dto.setBirthDate(new Date());
        dto.getAddresses().add(createAddressDTO());
        return dto;
    }
}
