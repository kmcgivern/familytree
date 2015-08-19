package uk.co.kstech.dao.person;

import org.springframework.data.repository.CrudRepository;
import uk.co.kstech.model.person.Relationship;

/**
 * Created by KMcGivern on 08/11/2014.
 */
public interface RelationshipDao extends CrudRepository<Relationship, Long> {
}
