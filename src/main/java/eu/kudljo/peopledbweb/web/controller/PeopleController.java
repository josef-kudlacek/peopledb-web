package eu.kudljo.peopledbweb.web.controller;

import eu.kudljo.peopledbweb.business.model.Person;
import eu.kudljo.peopledbweb.data.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonRepository personRepository;

    public PeopleController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @ModelAttribute("people")
    public Iterable<Person> getPeople() {
        return personRepository.findAll();
    }

    @ModelAttribute
    public Person getPerson() {
        return new Person();
    }

    @PostMapping
    public String savePerson(@Valid Person person, Errors errors) {
        System.out.println(person);
        if (!errors.hasErrors()) {
            personRepository.save(person);
            return "redirect:people";
        }
        return "people";
    }

    @GetMapping
    public String showPeoplePage() {
        return "people";
    }
}
