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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class RelationshipAdapterImpl implements RelationshipAdapter {

	private RelationshipService relationshipService;
	
	private PersonService personService;
	
	private PersonAdapter personAdapter;

	@Autowired
	public RelationshipAdapterImpl(RelationshipService relationshipService, PersonService personService, PersonAdapter personAdapter){
        this.relationshipService = relationshipService;
		this.personService = personService;
		this.personAdapter = personAdapter;
	}

	@Override
	public Relationship toRelationship(final RelationshipDTO dto) {
		final Relationship relationship = getRelationship(dto);
		relationship.setPersonID1(dto.getPerson1().getId());
		relationship.setPersonID2(dto.getPerson2().getId());
		relationship.setRelationshipType(RelationshipType.valueOf(dto.getRelationshipType().toUpperCase()));
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(dto.getDateOfMarriage());
		relationship.setDateOfMarriage(cal);
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
		Date res = Date.from(model.getDateOfMarriage().toInstant());
		dto.setDateOfMarriage(res);
		return dto;
	}
	
	  private Relationship getRelationship(final RelationshipDTO dto) {
		  Relationship relationship = relationshipService.getRelationship(dto.getId());
	        if (relationship == null) {
                relationship = new Relationship();
	        }
	        return relationship;
	    }

}
