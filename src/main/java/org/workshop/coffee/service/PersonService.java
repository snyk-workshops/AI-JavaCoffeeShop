package org.workshop.coffee.service;

import org.workshop.coffee.domain.Person;
import org.workshop.coffee.domain.Role;
import org.workshop.coffee.exception.EmailTakenException;
import org.workshop.coffee.exception.UsernameTakenException;
import org.workshop.coffee.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person savePerson(Person person) {
        if (person.getPassword() != null && !person.getPassword().isEmpty()) {
            person.setEncryptedPassword(passwordEncoder.encode(person.getPassword()));
        }
        return personRepository.save(person);
    }

    public Person findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    public List<Person> findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public Person findById(Long id) {
        return personRepository.findById(id).get();
    }

    public void removePerson(Person person) {
        personRepository.delete(person);
    }

    public void removePerson(Long id) {
        personRepository.deleteById(id);
    }

    public Person registerNewPerson(Person person) throws EmailTakenException, UsernameTakenException {
        String username = person.getUsername();
        String email = person.getEmail();

        if (findByUsername(username) != null) {
            throw new UsernameTakenException("Username is already taken: " + username);
        }

        if (!findByEmail(email).isEmpty()) {
            throw new EmailTakenException("Email is already exists: " + email);
        }

        person.setRoles(Role.ROLE_CUSTOMER);

        return savePerson(person);
    }
}
