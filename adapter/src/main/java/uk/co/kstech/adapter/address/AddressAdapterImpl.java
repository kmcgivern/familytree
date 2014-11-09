package uk.co.kstech.adapter.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.dto.address.AddressDTO;
import uk.co.kstech.model.address.Address;
import uk.co.kstech.service.AddressService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KMcGivern on 24/04/2014.
 */
@Component
public class AddressAdapterImpl implements AddressAdapter {

    @Autowired
    private PersonAdapter personAdapter;

    @Autowired
    private AddressService addressService;

    @Override
    public List<Address> toAddress(final List<AddressDTO> list) {

        final List<Address> addressList = new ArrayList<>(list.size());
        for(AddressDTO dto : list){
            addressList.add(toAddress(dto));
        }
        return addressList;
    }

    @Override
    public Address toAddress(final AddressDTO dto) {
        final Address address = getAddress(dto);
        address.setFirstLine(dto.getFirstLine());
        address.setSecondLine(dto.getSecondLine());
        address.setPostCode(dto.getPostCode());
        address.setTown(dto.getTown());
        address.setPeople(personAdapter.toPeople(dto.getPeople()));
        return address;
    }

    @Override
    public AddressDTO toAddressDTO(final Address model) {
        final AddressDTO dto = new AddressDTO();
        dto.setTown(model.getTown());
        dto.setPostCode(model.getPostCode());
        dto.setFirstLine(model.getFirstLine());
        dto.setSecondLine(model.getSecondLine());
        dto.setId(model.getId());
        dto.setPeople(personAdapter.toPeopleDTO(model.getPeople()));
        return dto;
    }

    @Override
    public List<AddressDTO> toAddressDTO(final List<Address> list) {
        final List<AddressDTO> addressDtoList = new ArrayList<>(list.size());
        for(Address address : list){
            addressDtoList.add(toAddressDTO(address));
        }
        return addressDtoList;
    }

    private Address getAddress(final AddressDTO dto) {
        Address address = addressService.getAddress(dto.getId());
        if (address == null) {
            address = new Address();
        }
        return address;
    }
}
