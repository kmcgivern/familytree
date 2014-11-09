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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by KMcGivern on 24/04/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestAdapterConfig.class})
public class TestAddressAdapter {

    @InjectMocks
    @Autowired
    private AddressAdapter classUnderTest;

    @Mock
    private AddressService mockAddressService;

    @Mock
    private PersonAdapter mockPersonAdapter;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCopyAddressToDTO() {
        Address model = createAddress();
        when(mockPersonAdapter.toPeopleDTO(model.getPeople())).thenReturn(Arrays.asList(createPersonDTO()));

        final AddressDTO dto = classUnderTest.toAddressDTO(model);

        Mockito.verifyZeroInteractions(mockAddressService);

        verifyFields(dto, model);
        Assert.assertEquals(model.getId(), new Long(dto.getId()));
    }

    @Test
    public void shouldCopyAddressListToDTO() {
        Address model = createAddress();
        final List<Address> addressList = new ArrayList<>();
        addressList.add(model);

        when(mockPersonAdapter.toPeopleDTO(model.getPeople())).thenReturn(Arrays.asList(createPersonDTO()));
        final List<AddressDTO> dtoList = classUnderTest.toAddressDTO(addressList);
        Mockito.verifyZeroInteractions(mockAddressService);

        verifyFields(dtoList.get(0), model);
        Assert.assertEquals(model.getId(), new Long(dtoList.get(0).getId()));
    }

    @Test
    public void shouldCopyDTOToAddress() {
        AddressDTO dto = createAddressDTO();
        dto.setId(0L);
        when(mockAddressService.getAddress(dto.getId())).thenReturn(null);
        when(mockPersonAdapter.toPeople(dto.getPeople())).thenReturn(Arrays.asList(createPerson()));
        final Address model = classUnderTest.toAddress(dto);
        Mockito.validateMockitoUsage();
        verifyFields(dto, model);
        Assert.assertEquals(null, model.getId());
    }

    @Test
    public void shouldCopyAddressDTOListToAddress() {
        AddressDTO dto = createAddressDTO();
        dto.setId(0L);
        when(mockAddressService.getAddress(dto.getId())).thenReturn(null);
        when(mockPersonAdapter.toPeople(dto.getPeople())).thenReturn(Arrays.asList(createPerson()));

        final List<AddressDTO> addressDtoList = new ArrayList<>();
        addressDtoList.add(dto);

        final List<Address> addressList = classUnderTest.toAddress(addressDtoList);
        Mockito.validateMockitoUsage();
        verifyFields(dto, addressList.get(0));
        Assert.assertEquals(null, addressList.get(0).getId());
    }


    @Test
    public void shouldUpdateAddress() {
        AddressDTO dto = createAddressDTO();
        dto.setSecondLine("modified");

        Address originalFromDB = createAddress();

        when(mockPersonAdapter.toPeople(dto.getPeople())).thenReturn(Arrays.asList(createPerson()));
        when(mockAddressService.getAddress(dto.getId())).thenReturn(originalFromDB);
        final Address model = classUnderTest.toAddress(dto);
        verifyFields(dto, model);
    }

    private void verifyFields(final AddressDTO dto, final Address model) {
        Assert.assertThat(model.getFirstLine(), Matchers.equalToIgnoringWhiteSpace(dto.getFirstLine()));
        Assert.assertThat(model.getSecondLine(), Matchers.equalToIgnoringWhiteSpace(dto.getSecondLine()));
        Assert.assertThat(model.getTown(), Matchers.equalToIgnoringWhiteSpace(dto.getTown()));
        Assert.assertThat(model.getPostCode(), Matchers.equalToIgnoringWhiteSpace(dto.getPostCode()));

        Assert.assertThat(model.getPeople().get(0).getFirstName(), Matchers.equalToIgnoringWhiteSpace(dto.getPeople().get(0).getFirstName()));
    }

    private Address createAddress() {
        Address address = new Address();
        address.setFirstLine("1 New Street");
        address.setSecondLine("");
        address.setTown("Belfast");
        address.setPostCode("BT1 1AB");
        address.setId(1L);
        address.getPeople().add(createPerson());
        return address;
    }

    private Person createPerson() {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("Bob");
        person.setMiddleName("Chaz");
        person.setLastName("Davids");
        person.setBirthDate(new Date());
        return person;
    }

    private AddressDTO createAddressDTO() {
        AddressDTO dto = new AddressDTO();
        dto.setFirstLine("1 New Street");
        dto.setSecondLine("");
        dto.setTown("Belfast");
        dto.setPostCode("BT1 1AB");
        dto.setId(1L);
        dto.getPeople().add(createPersonDTO());
        return dto;
    }

    private PersonDTO createPersonDTO() {
        PersonDTO dto = new PersonDTO();
        dto.setFirstName("Bob");
        dto.setMiddleName("Chaz");
        dto.setLastName("Davids");
        dto.setBirthDate(new Date());
        return dto;
    }
}


