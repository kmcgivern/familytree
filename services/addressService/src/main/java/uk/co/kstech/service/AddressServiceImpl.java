package uk.co.kstech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.kstech.dao.address.AddressDao;
import uk.co.kstech.model.address.Address;
import uk.co.kstech.service.message.ConstraintError;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by KMcGivern on 10/04/2014.
 */
@Service(value ="AddressServiceImpl" )
public class AddressServiceImpl implements AddressService {

    private static Validator validator;

    @PostConstruct
    public static void setUpValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Autowired
    private AddressDao addressDao;

    @Override
    public Address getAddress(final long id) {
        return addressDao.findOne(id);
    }

    @Override
    public List<Address> getAddresses() {
        return makeList(addressDao.findAll());
    }

    @Override
    public Address createAddress(final Address address) {
        validate(address);
        return addressDao.save(address);
    }

    @Override
    public Address updateAddress(final Address address) {
        validate(address);
        return addressDao.save(address);
    }

    @Override
    public void deleteAddress(final Address address) {
        addressDao.delete(address);
    }

    private void validate(final Address address) {
        final Set<ConstraintViolation<Address>> constraintViolations = validator.validate(address);
        if (!constraintViolations.isEmpty()) {
            throw new AddressConstraintViolationException(formatViolations(constraintViolations));
        }
    }

    private <E> List<E> makeList(final Iterable<E> iter) {
        final List<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }

    private String formatViolations(final Set<ConstraintViolation<Address>> constraintViolations) {
        StringBuilder errors = new StringBuilder();
        errors.append("Address Constraint violation. ");
        for (ConstraintViolation<Address> cv : constraintViolations) {
            ConstraintError ce = new ConstraintError(cv.getPropertyPath().toString(), cv.getInvalidValue(), cv.getMessage());
            errors.append(ce.toString());
        }
        return errors.toString();
    }

    class AddressConstraintViolationException extends RuntimeException {

        public AddressConstraintViolationException(final String message) {
            super(message);
        }

    }
}
