package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final List<Person> persons;

    public PersonRepositoryImpl(DataLoader dataLoader) {
        this.persons = new ArrayList<>(dataLoader.getData().getPersons());
        log.info("PersonRepository initialized with {} persons.", persons.size());
    }

    @Override
    public List<Person> findAll() {
        log.debug("Retrieving all persons.");
        try {
            log.info("Successfully retrieved {} persons.", persons.size());
            return new ArrayList<>(persons);
        } catch (Exception e) {
            log.error("Error occurred while retrieving all persons.", e);
            throw e;
        }
    }

    @Override
    public Optional<Person> findPerson(String firstName, String lastName) {
        log.debug("Searching for person with name: {} {}", firstName, lastName);
        try {
            return persons.stream()
                          .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
                                  person.getLastName().equalsIgnoreCase(lastName))
                          .findFirst();
        } catch (Exception e) {
            log.error("Error occurred while searching for person: {} {}", firstName, lastName, e);
            throw e;
        }
    }

    @Override
    public void save(Person person) {
        log.info("Saving new person: {} {}", person.getFirstName(), person.getLastName());
        try {
            persons.add(person);
            log.debug("Person saved: {}", person);
        } catch (Exception e) {
            log.error("Error occurred while saving person: {} {}", person.getFirstName(), person.getLastName(), e);
            throw e;
        }
    }

    @Override
    public void delete(Person deletedPerson) {
        log.info("Attempting to delete person: {} {}", deletedPerson.getFirstName(), deletedPerson.getLastName());
        try {
            findPerson(deletedPerson.getFirstName(), deletedPerson.getLastName())
                    .ifPresent(person -> {
                        persons.remove(person);
                        log.debug("Person deleted: {}", person);
                    });
        } catch (Exception e) {
            log.error("Error occurred while deleting person: {} {}", deletedPerson.getFirstName(), deletedPerson.getLastName(), e);
            throw e;
        }
    }

    @Override
    public void update(Person updatedPerson) {
        log.info("Attempting to update person: {} {}", updatedPerson.getFirstName(), updatedPerson.getLastName());
        try {
            findPerson(updatedPerson.getFirstName(), updatedPerson.getLastName())
                    .ifPresent(personToUpdate -> {
                        personToUpdate.setAddress(updatedPerson.getAddress());
                        personToUpdate.setCity(updatedPerson.getCity());
                        personToUpdate.setZip(updatedPerson.getZip());
                        personToUpdate.setPhone(updatedPerson.getPhone());
                        personToUpdate.setEmail(updatedPerson.getEmail());
                        log.debug("Person updated: {}", personToUpdate);
                    });
        } catch (Exception e) {
            log.error("Error occurred while updating person: {} {}", updatedPerson.getFirstName(), updatedPerson.getLastName(), e);
            throw e;
        }
    }

    @Override
    public List<Person> findPersonsByAddress(String address) {
        log.debug("Searching for persons at address: {}", address);
        try {
            List<Person> result = persons.stream()
                                         .filter(person -> person.getAddress().equalsIgnoreCase(address))
                                         .toList();
            log.debug("Found {} persons at address: {}", result.size(), address);
            return result;
        } catch (Exception e) {
            log.error("Error occurred while searching for persons at address: {}", address, e);
            throw e;
        }
    }

    @Override
    public List<Person> findPersonsByCity(String city) {
        log.debug("Searching for persons in city: {}", city);
        try {
            List<Person> result = persons.stream()
                                         .filter(person -> person.getCity().equalsIgnoreCase(city))
                                         .collect(Collectors.toList());
            log.debug("Found {} persons in city: {}", result.size(), city);
            return result;
        } catch (Exception e) {
            log.error("Error occurred while searching for persons in city: {}", city, e);
            throw e;
        }
    }

    @Override
    public List<Person> findPersonsByLastName(String lastName) {
        log.debug("Searching for persons with last name: {}", lastName);
        try {
            List<Person> result = persons.stream()
                                         .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                                         .collect(Collectors.toList());
            log.debug("Found {} persons with last name: {}", result.size(), lastName);
            return result;
        } catch (Exception e) {
            log.error("Error occurred while searching for persons with last name: {}", lastName, e);
            throw e;
        }
    }
}
