package uk.co.kstech.service;

import uk.co.kstech.model.address.Address;

import java.util.List;

/**
 * Created by KMcGivern on 10/04/2014.
 */
public interface AddressService {

    Address getAddress(final long id);

    List<Address> getAddresses();

    Address createAddress(final Address address);

    Address updateAddress(final Address address);

    void deleteAddress(final Address address);
}
