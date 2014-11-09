package uk.co.kstech.adapter.address;

import uk.co.kstech.dto.address.AddressDTO;
import uk.co.kstech.model.address.Address;

import java.util.List;

/**
 * Created by KMcGivern on 24/04/2014.
 */
public interface AddressAdapter {

    public List<Address> toAddress(final List<AddressDTO> list);

    public Address toAddress(final AddressDTO dto);

    public AddressDTO toAddressDTO(final Address model);

    public List<AddressDTO> toAddressDTO(final List<Address> list);
}
