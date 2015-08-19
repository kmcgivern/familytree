package uk.co.kstech.adapter.relationship;

import uk.co.kstech.dto.RelationshipDTO;
import uk.co.kstech.model.person.Relationship;

public interface RelationshipAdapter {
	
	  public Relationship toRelationship(final RelationshipDTO dto);

	    public RelationshipDTO toRelationshipDTO(final Relationship model);

}
