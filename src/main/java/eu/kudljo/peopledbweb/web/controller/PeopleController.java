package eu.kudljo.peopledbweb.web.controller;

import eu.kudljo.peopledbweb.business.model.Person;
import eu.kudljo.peopledbweb.business.service.PersonService;
import eu.kudljo.peopledbweb.data.FileStorageRepository;
import eu.kudljo.peopledbweb.data.PersonRepository;
import eu.kudljo.peopledbweb.exception.StorageException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Controller
@RequestMapping("/people")
@Log4j2
public class PeopleController {

    public static final String DISPOSITION = """
             attachment; filename="%s"
            """;
    private final PersonRepository personRepository;

    private final FileStorageRepository fileStorageRepository;

    private final PersonService personService;

    public PeopleController(PersonRepository personRepository,
                            FileStorageRepository fileStorageRepository,
                            PersonService personService) {
        this.personRepository = personRepository;
        this.fileStorageRepository = fileStorageRepository;
        this.personService = personService;
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
    public String savePerson(Model model, @Valid Person person, Errors errors, @RequestParam("photoFileName") MultipartFile photoFile) throws IOException {
        log.info(person);
        log.info("Filename: " + photoFile.getOriginalFilename());
        log.info("File size: " + photoFile.getSize());
        log.info("Errors: " + errors);
        if (!errors.hasErrors()) {
            try {
                personService.save(person, photoFile.getInputStream());
                return "redirect:people";
            } catch (StorageException e) {
                model.addAttribute("errorMsg", "System is currently unable to accept photo fies in this time.");
                return "people";
            }
        }
        return "people";
    }

    @GetMapping
    public String showPeoplePage() {
        return "people";
    }

    @GetMapping("/images/{resource}")
    public ResponseEntity<Resource> getResource(@PathVariable String resource) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, format(DISPOSITION, resource))
                .body(fileStorageRepository.findByName(resource));
    }

    @PostMapping(params = "delete=true")
    public String deletePeople(@RequestParam Optional<List<Long>> selections) {
        log.info(selections);
        if (selections.isPresent()) {
            personService.deleteAllById(selections.get());
        }
        return "redirect:people";
    }

    @PostMapping(params = "edit=true")
    public String edit(@RequestParam Optional<List<Long>> selections, Model model) {
        log.info(selections);
        if (selections.isPresent()) {
            Optional<Person> person = personRepository.findById(selections.get().get(0));
            model.addAttribute("person", person);
        }
        return "people";
    }
}
