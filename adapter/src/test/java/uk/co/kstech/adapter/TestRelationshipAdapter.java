package uk.co.kstech.adapter;

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

import uk.co.kstech.adapter.relationship.RelationshipAdapter;
import uk.co.kstech.dto.RelationshipDTO;
import uk.co.kstech.service.PersonService;
import uk.co.kstech.service.RelationshipService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestAdapterConfig.class })
public class TestRelationshipAdapter {

	@InjectMocks
	@Autowired
	private RelationshipAdapter classUnderTest;

	@Mock
	private RelationshipService mockRelationshipService;

	@Mock
	private PersonService mockPersonService;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldCopyDTOToModel() {

	}

}
