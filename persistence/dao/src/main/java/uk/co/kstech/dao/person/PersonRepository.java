package uk.co.kstech.dao.person;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.kstech.model.person.Person;

/**
 * Created by KMcGivern on 7/17/2014.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
