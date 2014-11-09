package uk.co.kstech.rest.service.address;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.co.kstech.adapter.address.AddressAdapter;
import uk.co.kstech.dto.address.AddressDTO;
import uk.co.kstech.model.address.Address;
import uk.co.kstech.rest.config.TestRestConfig;
import uk.co.kstech.rest.service.utilities.DtoBuilder;
import uk.co.kstech.rest.service.utilities.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by KMcGivern on 28/04/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestRestConfig.class})
public class IntegrationTest {


    private MockMvc mockMvc;


    @InjectMocks
    @Autowired
    private AddressService classUnderTest;

    @Autowired
    private JsonUtils<AddressDTO> jsonUtils;

    @Mock
    private uk.co.kstech.service.AddressService mockAddressService;

    @Mock
    private AddressAdapter mockAddressAdapter;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build();
    }

    @Test
    public void shouldCreateAddress() throws Exception {
        AddressDTO dto = DtoBuilder.createAddressDTO();
        final Address address = DtoBuilder.convertAddressDTO(dto);

        when(mockAddressAdapter.toAddress(dto)).thenReturn(address);
        when(mockAddressService.createAddress(address)).thenReturn(address);
        when(mockAddressAdapter.toAddressDTO(address)).thenReturn(dto);

        String json = jsonUtils.convertToJson(dto);

        this.mockMvc.perform(post("/addresses").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldUpdateAddress() throws Exception {
        AddressDTO addressDTO = DtoBuilder.createAddressDTO();
        addressDTO.setId(1);
        final Address address = DtoBuilder.convertAddressDTO(addressDTO);
        address.setVersion(1L);
        when(mockAddressService.getAddress(1)).thenReturn(address);
        when(mockAddressAdapter.toAddress(addressDTO)).thenReturn(address);
        when(mockAddressService.updateAddress(address)).thenReturn(address);
        when(mockAddressAdapter.toAddressDTO(address)).thenReturn(addressDTO);

        String json = jsonUtils.convertToJson(addressDTO);
        this.mockMvc.perform(put("/addresses").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldGetAddress() throws Exception {
        AddressDTO dto = DtoBuilder.createAddressDTO();
        final Address address = DtoBuilder.convertAddressDTO(dto);
        when(mockAddressAdapter.toAddressDTO(address)).thenReturn(dto);
        when(mockAddressService.getAddress(1)).thenReturn(address);

        this.mockMvc.perform(get("/addresses?Id=1")).andExpect(status().isOk());
        Mockito.validateMockitoUsage();
    }


    @Test
    public void shouldGetAllAddresses() throws Exception {
        AddressDTO dto = DtoBuilder.createAddressDTO();
        final Address address = DtoBuilder.convertAddressDTO(dto);
        final List<Address> addresses = new ArrayList<>();
        final List<AddressDTO> addressDTOs = new ArrayList<>();
        addressDTOs.add(dto);
        addresses.add(address);

        when(mockAddressAdapter.toAddressDTO(addresses)).thenReturn(addressDTOs);
        when(mockAddressService.getAddresses()).thenReturn(addresses);

        this.mockMvc.perform(get("/addresses/all")).andExpect(status().isOk());
        Mockito.validateMockitoUsage();
    }


}
