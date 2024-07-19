package eu.kudljo.peopledbweb.data;

import eu.kudljo.peopledbweb.business.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}
