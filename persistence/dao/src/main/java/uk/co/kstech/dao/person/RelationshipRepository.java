package uk.co.kstech.dao.person;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.kstech.model.person.Relationship;

/**
 * Created by KMcGivern on 08/11/2014.
 */
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
}
