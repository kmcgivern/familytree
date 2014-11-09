package uk.co.kstech.service;

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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import uk.co.kstech.dao.address.AddressDao;
import uk.co.kstech.model.address.Address;
import uk.co.kstech.service.config.TestServiceConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by KMcGivern on 10/04/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestServiceConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AddressServiceTest {

    @InjectMocks
    @Autowired
    private AddressService classUnderTest;

    @Mock
    private AddressDao mockDao;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAddress() {
        when(mockDao.findOne(1L)).thenReturn(createAddress());
        classUnderTest.getAddress(1L);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldGetAllAddresses() {
        Iterable<Address> iterable = new ArrayList();
        ((ArrayList) iterable).add(createAddress());
        when(mockDao.findAll()).thenReturn(iterable);
        final List<Address> addresses = classUnderTest.getAddresses();
        Mockito.validateMockitoUsage();
        Assert.assertThat(addresses.size(), Matchers.equalTo(1));
    }

    @Test
    public void shouldCreateAddress() {
        Address address = createAddress();
        when(mockDao.save(address)).thenReturn(address);
        classUnderTest.createAddress(address);
        Mockito.validateMockitoUsage();
    }

    @Test(expected = AddressServiceImpl.AddressConstraintViolationException.class)
    public void shouldFailValidationOnCreate() {
        Address address = createAddress();
        address.setFirstLine(null);
        classUnderTest.createAddress(address);
        Mockito.verifyZeroInteractions(mockDao);
    }

    @Test
    public void shouldUpdateAddress() {
        Address address = createAddress();
        when(mockDao.save(address)).thenReturn(address);
        classUnderTest.updateAddress(address);
        Mockito.validateMockitoUsage();
    }

    @Test(expected = AddressServiceImpl.AddressConstraintViolationException.class)
    public void shouldFailValidationOnUpdate() {
        Address address = createAddress();
        address.setFirstLine(null);
        classUnderTest.updateAddress(address);
        Mockito.verifyZeroInteractions(mockDao);
    }

    @Test
    public void shouldDeleteAddress() {
        Address address = createAddress();
        doNothing().when(mockDao).delete(address);
        classUnderTest.deleteAddress(address);
        Mockito.validateMockitoUsage();
    }

    private Address createAddress() {
        Address address = new Address();
        address.setFirstLine("1 New Street");
        address.setSecondLine("");
        address.setTown("Belfast");
        address.setPostCode("BT1 1AB");

        return address;
    }
}
