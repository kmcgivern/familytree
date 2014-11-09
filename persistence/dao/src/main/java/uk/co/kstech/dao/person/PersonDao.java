package uk.co.kstech.dao.person;

import org.springframework.data.repository.CrudRepository;
import uk.co.kstech.model.person.Person;

/**
 * Created by KMcGivern on 7/17/2014.
 */
public interface PersonDao extends CrudRepository<Person, Long> {
}
