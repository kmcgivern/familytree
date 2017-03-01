package uk.co.kstech.service;

import com.flextrade.jfixture.annotations.Fixture;
import com.flextrade.jfixture.rules.FixtureRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.kstech.dao.person.RelationshipRepository;
import uk.co.kstech.model.person.Relationship;
import uk.co.kstech.model.person.Relationship.RelationshipType;
import uk.co.kstech.service.RelationshipServiceImpl.RelationshipConstraintViolationException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * Created by KMcGivern on 7/17/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RelationshipServiceTest {

    @Configuration
    public static class Config {

        @Bean
        public RelationshipService getRelationshipService() {
            return new RelationshipServiceImpl(getRelationshipRepository());
        }

        @Bean
        public RelationshipRepository getRelationshipRepository() {
            return Mockito.mock(RelationshipRepository.class);
        }
    }

    @Rule
    public FixtureRule fr = FixtureRule.initFixtures();

    @Fixture
    private Relationship mockRelationship;

    @Autowired
    private RelationshipService classUnderTest;

    @Autowired
    private RelationshipRepository mockDao;

    @Before
    public void setUp() {
        reset(mockDao);
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
        mockRelationship.setRelationshipType(RelationshipType.BROTHER);
        return mockRelationship;
    }


}
