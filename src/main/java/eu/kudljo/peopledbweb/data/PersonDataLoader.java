package eu.kudljo.peopledbweb.data;

import eu.kudljo.peopledbweb.business.model.Person;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;

//@Component
public class PersonDataLoader implements ApplicationRunner {

    private PersonRepository personRepository;

    public PersonDataLoader(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (personRepository.count() == 0) {
            List<Person> people = List.of(
//                    new Person(null, "Pete", "Snake", LocalDate.of(1950, 1, 1), new BigDecimal("50000"), "dummy@sample.com"),
//                    new Person(null, "Jennifer", "Smith", LocalDate.of(1960, 2, 1), new BigDecimal("60000"), "dummy@sample.com"),
//                    new Person(null, "Mark", "Jackson", LocalDate.of(1970, 3, 1), new BigDecimal("70000"), "dummy@sample.com"),
//                    new Person(null, "Vishnu", "McGuire", LocalDate.of(1997, 3, 1), new BigDecimal("70000"), "dummy@sample.com"),
//                    new Person(null, "Alice", "Smith", LocalDate.of(1970, 3, 1), new BigDecimal("70000"), "dummy@sample.com"),
//                    new Person(null, "Akira", "Kim", LocalDate.of(1980, 4, 1), new BigDecimal("80000"), "dummy@sample.com")
            );
            personRepository.saveAll(people);
        }
    }
}
