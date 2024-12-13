package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing persons.
 * Provides endpoints to retrieve, add, update, and delete person records.
 */
@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    /**
     * Retrieves a list of all persons in the repository.
     */
    @GetMapping
    public List<Person> getAllPersons() {
        log.info("Received request to get all persons.");
        try {
            List<Person> persons = personRepository.findAll();
            log.debug("Fetched persons: {}", persons);
            log.info("Fetched {} persons.", persons.size());
            return persons;
        } catch (Exception e) {
            log.error("Error occurred while fetching all persons.", e);
            throw e;
        }
    }

    /**
     * Adds a new person to the repository.
     */
    @PostMapping
    public String addOnePerson(@RequestBody Person person) {
        log.info("Received request to add a new person: {}", person);
        try {
            personRepository.save(person);
            log.debug("Person saved: {}", person);
            log.info("Person added successfully: {} {}", person.getFirstName(), person.getLastName());
            return "Person added successfully!";
        } catch (Exception e) {
            log.error("Error occurred while adding a new person: {}", person, e);
            return "Failed to add the person.";
        }
    }

    /**
     * Deletes an existing person from the repository.
     */
    @DeleteMapping
    public ResponseEntity<Object> deleteOnePerson(@RequestBody Person person) {
        log.info("Received request to delete person: {}", person);
        try {
            Optional<Person> existingPerson = personRepository.findPerson(person.getFirstName(), person.getLastName());
            if (!existingPerson.isPresent()) {
                log.info("Person not found: {} {}", person.getFirstName(), person.getLastName());
                return ResponseEntity.ok().body("{}");
            }
            personRepository.delete(person);
            log.debug("Person deleted: {}", person);
            log.info("Person deleted successfully");
            return ResponseEntity.ok().body(person);
        } catch (Exception e) {
            log.error("Error occurred while deleting person: {}", person, e);
            return ResponseEntity.badRequest().body("Failed to delete the person.");
        }
    }

    /**
     * Updates an existing person's details in the repository.
     */
    @PutMapping
    public ResponseEntity<Object> updateOnePerson(@RequestBody Person person) {
        log.info("Received request to update person: {}", person);
        try {
            Optional<Person> existingPerson = personRepository.findPerson(person.getFirstName(), person.getLastName());
            if (!existingPerson.isPresent()) {
                log.info("Person not found: {} {}", person.getFirstName(), person.getLastName());
                return ResponseEntity.ok().body("{}");
            }
            personRepository.update(person);
            log.debug("Person updated: {}", person);
            log.info("Person updated successfully");
            return ResponseEntity.ok().body(person);
        } catch (Exception e) {
            log.error("Error occurred while updating person: {}", person, e);
            return ResponseEntity.badRequest().body("Failed to update the person.");
        }
    }
}
