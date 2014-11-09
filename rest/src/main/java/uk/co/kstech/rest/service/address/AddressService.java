package uk.co.kstech.rest.service.address;

import uk.co.kstech.dto.address.AddressDTO;

import java.util.List;

/**
 * Created by KMcGivern on 24/04/2014.
 */
public interface AddressService {

    AddressDTO getAddress(long Id);

    List<AddressDTO> getAddresses();

    AddressDTO createAddress(final AddressDTO address);

    AddressDTO updateAddress(final AddressDTO address);

    void deleteAddress(final long Id);
}
