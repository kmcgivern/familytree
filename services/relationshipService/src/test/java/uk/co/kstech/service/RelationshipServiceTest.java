package uk.co.kstech.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import uk.co.kstech.dao.person.RelationshipDao;
import uk.co.kstech.model.person.Relationship;
import uk.co.kstech.model.person.Relationship.RelationshipType;
import uk.co.kstech.service.RelationshipServiceImpl.RelationshipConstraintViolationException;
import autofixture.publicinterface.Fixture;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@RunWith(MockitoJUnitRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RelationshipServiceTest {

    @InjectMocks
    private RelationshipService classUnderTest = new RelationshipServiceImpl();;

    @Mock
    private RelationshipDao mockDao;

    @Before
    public void initMocks() {
    	RelationshipServiceImpl.setUpValidation();
    }

    @Test
    public void shouldGetRelationship() {
        when(mockDao.findOne(1L)).thenReturn(createRelationship());
        classUnderTest.getRelationship(1L);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void shouldCreateRelationship() {
    	Relationship r = createRelationship();
        when(mockDao.save(r)).thenReturn(r);
        classUnderTest.createRelationship(r);
        Mockito.validateMockitoUsage();
    }

    @Test(expected = RelationshipConstraintViolationException.class)
    public void shouldFailValidationOnCreate() {
    	Relationship r = createRelationship();
        r.setPersonID1(null);
        classUnderTest.createRelationship(r);
        Mockito.verifyZeroInteractions(mockDao);
    }

    @Test
    public void shouldUpdatRelationship() {
    	Relationship r = createRelationship();
        when(mockDao.save(r)).thenReturn(r);
        classUnderTest.updateRelationship(r);
        Mockito.validateMockitoUsage();
    }

    @Test(expected = RelationshipConstraintViolationException.class)
    public void shouldFailValidationOnUpdate() {
    	Relationship r = createRelationship();
    	r.setPersonID1(null);
        classUnderTest.updateRelationship(r);
        Mockito.verifyZeroInteractions(mockDao);
    }

    @Test
    public void shouldDeleteRelationship() {
    	Relationship r = createRelationship();
        doNothing().when(mockDao).delete(r);
        classUnderTest.deleteRelationship(r);
        Mockito.validateMockitoUsage();
    }

    private Relationship createRelationship() {
    	Fixture fixture = new Fixture();
    	Relationship r = fixture.create(Relationship.class);
        r.setRelationshipType(RelationshipType.BROTHER);
        return r;
    }


}
