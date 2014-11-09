package uk.co.kstech.rest.service.address;

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
import uk.co.kstech.adapter.address.AddressAdapter;
import uk.co.kstech.dto.address.AddressDTO;
import uk.co.kstech.model.address.Address;
import uk.co.kstech.rest.config.TestRestConfig;
import uk.co.kstech.rest.service.utilities.DtoBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by KMcGivern on 28/04/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRestConfig.class})
public class TestRestAddressService {

    @InjectMocks
    @Autowired
    private AddressService classUnderTest;

    @Mock
    private uk.co.kstech.service.AddressService mockAddressService;

    @Mock
    private AddressAdapter addressAdapter;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateAddress() {
        AddressDTO dto = DtoBuilder.createAddressDTO();
        final Address address = DtoBuilder.convertAddressDTO(dto);
        when(addressAdapter.toAddress(dto)).thenReturn(address);
        when(addressAdapter.toAddressDTO(address)).thenReturn(dto);
        when(mockAddressService.createAddress(address)).thenReturn(address);

        classUnderTest.createAddress(dto);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldUpdateAddress() {
        AddressDTO dto = DtoBuilder.createAddressDTO();
        dto.setId(1);
        final Address address = DtoBuilder.convertAddressDTO(dto);
        when(addressAdapter.toAddress(dto)).thenReturn(address);
        when(addressAdapter.toAddressDTO(address)).thenReturn(dto);
        when(mockAddressService.getAddress(1)).thenReturn(address);
        when(mockAddressService.updateAddress(address)).thenReturn(address);

        classUnderTest.updateAddress(dto);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldGetAddress() {
        AddressDTO dto = DtoBuilder.createAddressDTO();
        final Address address = DtoBuilder.convertAddressDTO(dto);
        when(addressAdapter.toAddressDTO(address)).thenReturn(dto);
        when(mockAddressService.getAddress(1)).thenReturn(address);

        classUnderTest.createAddress(dto);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldGetAllAddresses() {
        AddressDTO dto = DtoBuilder.createAddressDTO();
        final Address address = DtoBuilder.convertAddressDTO(dto);
        final List<Address> addresses = new ArrayList<>();
        final List<AddressDTO> addressDTOs = new ArrayList<>();
        addressDTOs.add(dto);
        addresses.add(address);

        when(addressAdapter.toAddressDTO(addresses)).thenReturn(addressDTOs);
        when(mockAddressService.getAddresses()).thenReturn(addresses);

        classUnderTest.getAddresses();
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldDeleteAddress() {
        AddressDTO dto = DtoBuilder.createAddressDTO();
        final Address address = DtoBuilder.convertAddressDTO(dto);

        when(mockAddressService.getAddress(1L)).thenReturn(address);
        doNothing().when(mockAddressService).deleteAddress(address);

        classUnderTest.deleteAddress(1L);
        Mockito.validateMockitoUsage();
    }

    @Test(expected = RestAddressService.AddressNotFoundException.class)
    public void shouldThrowExceptionOnDeletionOfInvalidAddress() {

        when(mockAddressService.getAddress(1L)).thenReturn(null);

        classUnderTest.deleteAddress(1L);
        Mockito.validateMockitoUsage();
    }

}
