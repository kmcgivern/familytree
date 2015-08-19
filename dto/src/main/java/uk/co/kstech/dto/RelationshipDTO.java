package uk.co.kstech.dto;

import uk.co.kstech.dto.person.PersonDTO;

import java.util.Date;

/**
 * Created by KMCgivern on 07/11/2014.
 */
public class RelationshipDTO extends BaseDTO  {

    private PersonDTO person1;

    private PersonDTO person2;

    private Date dateOfMarriage;
    
    private String relationshipType;

	public PersonDTO getPerson1() {
		return person1;
	}

	public void setPerson1(PersonDTO person1) {
		this.person1 = person1;
	}

	public PersonDTO getPerson2() {
		return person2;
	}

	public void setPerson2(PersonDTO person2) {
		this.person2 = person2;
	}

	public Date getDateOfMarriage() {
		return dateOfMarriage;
	}

	public void setDateOfMarriage(Date dateOfMarriage) {
		this.dateOfMarriage = dateOfMarriage;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}
    
}
