package eu.kudljo.peopledbweb.business.service;

import eu.kudljo.peopledbweb.business.model.Person;
import eu.kudljo.peopledbweb.data.FileStorageRepository;
import eu.kudljo.peopledbweb.data.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final FileStorageRepository fileStorageRepository;

    public PersonService(PersonRepository personRepository, FileStorageRepository fileStorageRepository) {
        this.personRepository = personRepository;
        this.fileStorageRepository = fileStorageRepository;
    }

    @Transactional
    public Person save(Person person, InputStream photoStream) {
        Person savedPerson = personRepository.save(person);
        fileStorageRepository.save(person.getPhotoFileName(), photoStream);
        return savedPerson;
    }

    public Optional<Person> findById(Long aLong) {
        return personRepository.findById(aLong);
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional
    public void deleteAllById(Iterable<Long> ids) {
        Set<String> fileNames = personRepository.findFileNamesByIds(ids);
        personRepository.deleteAllById(ids);
        fileStorageRepository.deleteAllByName(fileNames);
    }
}
