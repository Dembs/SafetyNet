package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String deleteOnePerson(@RequestBody Person person) {
        log.info("Received request to delete person: {}", person);
        try {
            personRepository.delete(person);
            log.debug("Person deleted: {}", person);
            log.info("Person deleted successfully: {} {}", person.getFirstName(), person.getLastName());
            return "Person deleted successfully";
        } catch (Exception e) {
            log.error("Error occurred while deleting person: {}", person, e);
            return "Failed to delete the person.";
        }
    }

    /**
     * Updates an existing person's details in the repository.
     */
    @PutMapping
    public String updateOnePerson(@RequestBody Person person) {
        log.info("Received request to update person: {}", person);
        try {
            personRepository.update(person);
            log.debug("Person updated: {}", person);
            log.info("Person updated successfully: {} {}", person.getFirstName(), person.getLastName());
            return "Person updated successfully";
        } catch (Exception e) {
            log.error("Error occurred while updating person: {}", person, e);
            return "Failed to update the person.";
        }
    }
}
