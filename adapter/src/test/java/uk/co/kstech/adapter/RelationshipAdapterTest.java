package uk.co.kstech.adapter;

import static org.mockito.Mockito.when;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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
import autofixture.publicinterface.Fixture;

@RunWith(MockitoJUnitRunner.class)
public class RelationshipAdapterTest {

	@InjectMocks
	private RelationshipAdapter classUnderTest = new RelationshipAdapterImpl();
	
	@Mock
	private PersonAdapter pAdapter;

	@Mock
	private RelationshipService mockRelationshipService;

	@Mock
	private PersonService mockPersonService;

	@Before
	public void initMocks() {

	}

	@Test
	public void shouldCreateNewModelFromDTO() {
		Fixture fixture = new Fixture();
		// arrange
		RelationshipDTO dto = fixture.create(RelationshipDTO.class);
		dto.setRelationshipType("BROTHER");
		when(mockRelationshipService.getRelationship(dto.getId())).thenReturn(null);
		// act
		final Relationship model = classUnderTest.toRelationship(dto);
		// assert
		Mockito.validateMockitoUsage();
		verifyFields(dto, model);
		Assert.assertEquals(null, model.getId());
	}

	@Test
	public void shouldCopyDTOToModel() {
		Fixture fixture = new Fixture();
		// arrange
		RelationshipDTO dto = fixture.create(RelationshipDTO.class);
		Relationship model = fixture.create(Relationship.class);
		dto.setRelationshipType("BROTHER");
		when(mockRelationshipService.getRelationship(dto.getId())).thenReturn(model);
		// act
		final Relationship modelAdapted = classUnderTest.toRelationship(dto);
		// assert
		Mockito.validateMockitoUsage();
		verifyFields(dto, modelAdapted);
		Assert.assertEquals(model.getId(), modelAdapted.getId());
	}
	
	@Test
	public void shouldCopyModelToDTO() {
		Fixture fixture = new Fixture();
		// arrange
		Relationship model = fixture.create(Relationship.class);
		model.setRelationshipType(RelationshipType.FATHER);
		Person p1 = fixture.create(Person.class);
		when(mockPersonService.getPerson(model.getPersonID1())).thenReturn(p1);
		Person p2 = fixture.create(Person.class);
		when(mockPersonService.getPerson(model.getPersonID2())).thenReturn(p2);
		PersonDTO p1Dto = fixture.create(PersonDTO.class);
		when(pAdapter.toPersonDTO(p1)).thenReturn(p1Dto);
		PersonDTO p2Dto = fixture.create(PersonDTO.class);
		when(pAdapter.toPersonDTO(p2)).thenReturn(p2Dto);
		// act
		final RelationshipDTO dtoAdapted = classUnderTest.toRelationshipDTO(model);
		// assert
		Mockito.validateMockitoUsage();
		//verifyFields(dtoAdapted, model);
		Assert.assertEquals(model.getId().longValue(), dtoAdapted.getId());
		Assert.assertNotNull(dtoAdapted.getPerson1());
		Assert.assertNotNull(dtoAdapted.getPerson2());
	}

	private void verifyFields(final RelationshipDTO dto, final Relationship model) {
		Assert.assertThat(model.getPersonID1(), Matchers.equalTo(dto.getPerson1().getId()));
		Assert.assertThat(model.getPersonID2(), Matchers.equalTo(dto.getPerson2().getId()));
		Assert.assertThat(model.getRelationshipType().toString(), Matchers.equalToIgnoringWhiteSpace(dto.getRelationshipType()));
	}

}
