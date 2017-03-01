package uk.co.kstech.adapter;

import com.flextrade.jfixture.annotations.Fixture;
import com.flextrade.jfixture.rules.FixtureRule;
import org.hamcrest.Matchers;
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
import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.adapter.relationship.RelationshipAdapter;
import uk.co.kstech.adapter.relationship.RelationshipAdapterImpl;
import uk.co.kstech.dto.RelationshipDTO;
import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.model.person.Relationship;
import uk.co.kstech.model.person.Relationship.RelationshipType;
import uk.co.kstech.service.PersonService;
import uk.co.kstech.service.RelationshipService;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RelationshipAdapterTest {

    @Configuration
    public static class Config {

        @Bean
        public RelationshipAdapter getRelationshipAdapter() {
            return new RelationshipAdapterImpl(getRelationshipService(), getPersonService(), getPersonAdapter());
        }

        @Bean
        public PersonAdapter getPersonAdapter() {
            return Mockito.mock(PersonAdapter.class);
        }

        @Bean
        public RelationshipService getRelationshipService() {
            return Mockito.mock(RelationshipService.class);
        }

        @Bean
        public PersonService getPersonService() {
            return Mockito.mock(PersonService.class);
        }
    }

    @Rule
    public FixtureRule fr = FixtureRule.initFixtures();

    @Fixture
    private Person mockPerson1;

    @Fixture
    private Person mockPerson2;

    @Fixture
    private PersonDTO mockPersonDto1;

    @Fixture
    private PersonDTO mockPersonDto2;

    @Autowired
    private RelationshipAdapter classUnderTest;

    @Autowired
    private PersonAdapter mockPersonAdapter;

    @Autowired
    private RelationshipService mockRelationshipService;

    @Autowired
    private PersonService mockPersonService;

    @Before
    public void initMocks() {
        reset(mockPersonService, mockRelationshipService, mockPersonAdapter);
    }

    @Test
    public void shouldCreateNewModelFromDTO() {
        // arrange
        RelationshipDTO dto = getRelationshipDto();
        when(mockRelationshipService.getRelationship(dto.getId())).thenReturn(null);
        // act
        final Relationship model = classUnderTest.toRelationship(dto);
        // assert
        Mockito.validateMockitoUsage();
        verifyFields(dto, model);
        assertEquals(null, model.getId());
    }

    @Test
    public void shouldCopyDTOToModel() {

        // arrange
        RelationshipDTO dto = getRelationshipDto();
        Relationship model = getRelationship();
        when(mockRelationshipService.getRelationship(dto.getId())).thenReturn(model);
        // act
        final Relationship modelAdapted = classUnderTest.toRelationship(dto);
        // assert
        Mockito.validateMockitoUsage();
        verifyFields(dto, modelAdapted);
        assertEquals(model.getId(), modelAdapted.getId());
    }

    @Test
    public void shouldCopyModelToDTO() {

        // arrange
        Relationship model = getRelationship();
        mockPersonDto1.setId(model.getPersonID1());
        mockPersonDto2.setId(model.getPersonID2());

        when(mockPersonService.getPerson(model.getPersonID1())).thenReturn(mockPerson1);

        when(mockPersonService.getPerson(model.getPersonID2())).thenReturn(mockPerson2);

        when(mockPersonAdapter.toPersonDTO(mockPerson1)).thenReturn(mockPersonDto1);

        when(mockPersonAdapter.toPersonDTO(mockPerson2)).thenReturn(mockPersonDto2);

        // act
        final RelationshipDTO dtoAdapted = classUnderTest.toRelationshipDTO(model);
        // assert
        Mockito.validateMockitoUsage();
        verifyFields(dtoAdapted, model);
        assertEquals(model.getId().longValue(), dtoAdapted.getId());
        assertNotNull(dtoAdapted.getPerson1());
        assertNotNull(dtoAdapted.getPerson2());
    }

    private RelationshipDTO getRelationshipDto() {
        PersonDTO p1 = new PersonDTO();
        p1.setId(1L);
        PersonDTO p2 = new PersonDTO();
        p2.setId(2L);
        RelationshipDTO dto = new RelationshipDTO();
        dto.setPerson1(p1);
        dto.setPerson2(p2);
        dto.setDateOfMarriage(Date.from(GregorianCalendar.getInstance().toInstant()));
        dto.setRelationshipType("MARRIED");
        return dto;
    }

    private Relationship getRelationship() {
        Relationship relationship = new Relationship(1L);
        relationship.setDateOfMarriage(GregorianCalendar.getInstance());
        relationship.setPersonID1(1L);
        relationship.setPersonID2(2L);
        relationship.setRelationshipType(RelationshipType.MARRIED);
        return relationship;
    }

    private void verifyFields(final RelationshipDTO dto, final Relationship model) {
        assertThat(model.getPersonID1(), Matchers.equalTo(dto.getPerson1().getId()));
        assertThat(model.getPersonID2(), Matchers.equalTo(dto.getPerson2().getId()));
        assertThat(model.getRelationshipType().toString(), Matchers.equalToIgnoringWhiteSpace(dto.getRelationshipType()));
    }

}
