package uk.co.kstech.adapter.relationship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.kstech.adapter.person.PersonAdapter;
import uk.co.kstech.dto.RelationshipDTO;
import uk.co.kstech.dto.person.PersonDTO;
import uk.co.kstech.model.person.Person;
import uk.co.kstech.model.person.Relationship;
import uk.co.kstech.model.person.Relationship.RelationshipType;
import uk.co.kstech.service.PersonService;
import uk.co.kstech.service.RelationshipService;

@Component
public class RelationshipAdapterImpl implements RelationshipAdapter {

	@Autowired
	private RelationshipService relationshipService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private PersonAdapter personAdapter;

	@Override
	public Relationship toRelationship(final RelationshipDTO dto) {
		final Relationship relationship = getRelationship(dto);
		relationship.setPersonID1(dto.getPerson1().getId());
		relationship.setPersonID2(dto.getPerson2().getId());
		relationship.setRelationshipType(RelationshipType.valueOf(dto.getRelationshipType().toUpperCase()));
		relationship.setDateOfMarriage(dto.getDateOfMarriage());
        return relationship;
	}

	@Override
	public RelationshipDTO toRelationshipDTO(final Relationship model) {
		RelationshipDTO dto = new RelationshipDTO();
		Person p1 = personService.getPerson(model.getPersonID1());
		PersonDTO p1Dto = personAdapter.toPersonDTO(p1);
		Person p2 = personService.getPerson(model.getPersonID2());
		PersonDTO p2Dto = personAdapter.toPersonDTO(p2);
		
		dto.setId(model.getId());
		dto.setPerson1(p1Dto);
		dto.setPerson2(p2Dto);
		dto.setRelationshipType(model.getRelationshipType().toString());
		dto.setDateOfMarriage(model.getDateOfMarriage());
		return dto;
	}
	
	  private Relationship getRelationship(final RelationshipDTO dto) {
		  Relationship person = relationshipService.getRelationship(dto.getId());
	        if (person == null) {
	            person = new Relationship();
	        }
	        return person;
	    }

}
