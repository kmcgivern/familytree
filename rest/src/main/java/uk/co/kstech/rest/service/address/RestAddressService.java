package uk.co.kstech.rest.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.co.kstech.adapter.address.AddressAdapter;
import uk.co.kstech.dto.address.AddressDTO;
import uk.co.kstech.model.address.Address;

import java.util.List;

/**
 * Created by KMcGivern on 24/04/2014.
 */
@RestController
@RequestMapping("/addresses")
public class RestAddressService implements AddressService {

    @Autowired
    private AddressAdapter addressAdapter;

    @Autowired
    private uk.co.kstech.service.AddressService addressService;

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public AddressDTO getAddress(@RequestParam(value = "Id", required = true) final long Id) {
        final Address address = addressService.getAddress(Id);
        AddressDTO dto = null;
        if (addressFound(address)) {
            dto = addressAdapter.toAddressDTO(address);
        } else {
            throw new AddressNotFoundException("Could not find Address for the given Address ID:" + Id);
        }
        return dto;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = "application/json")
    public List<AddressDTO> getAddresses() {
        final List<Address> addresses = addressService.getAddresses();
        return addressAdapter.toAddressDTO(addresses);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public AddressDTO createAddress(@RequestBody(required = true) final AddressDTO addressDTO) {
        final Address address = addressAdapter.toAddress(addressDTO);
        addressService.createAddress(address);
        return addressAdapter.toAddressDTO(address);
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public AddressDTO updateAddress(@RequestBody(required = true) final AddressDTO addressDTO) {
        final Address addressFromDb = addressService.getAddress(addressDTO.getId());
        final Address updated = addressAdapter.toAddress(addressDTO);
        updated.setVersion(addressFromDb.getVersion());
        addressService.updateAddress(updated);
        return addressAdapter.toAddressDTO(updated);
    }

    @Override
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAddress(@RequestBody(required = true) final long Id) {
        final Address address = addressService.getAddress(Id);
        if (addressFound(address)) {
            addressService.deleteAddress(address);
        } else {
            throw new AddressNotFoundException("Could not find Address for the given Address ID:" + Id);
        }
    }

    private boolean addressFound(final Address address) {
        return address != null;
    }

    public class AddressNotFoundException extends RuntimeException {

        public AddressNotFoundException() {
        }

        public AddressNotFoundException(final String error) {
            super(error);
        }

    }
}
