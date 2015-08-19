package uk.co.kstech.service;

import uk.co.kstech.model.person.Relationship;

/**
 * Created by KMcGivern on 7/17/2014.
 */
public interface RelationshipService {

	Relationship getRelationship(final long id);

	Relationship createRelationship(final Relationship Relationship);

	Relationship updateRelationship(final Relationship Relationship);

    void deleteRelationship(final Relationship Relationship);
}
