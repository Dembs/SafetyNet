package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Persons.

 */
public interface PersonRepository {

    /**
     * Retrieves all persons from the repository.
     *
     * @return a list of all `Person`
     */
    List<Person> findAll();

    /**
     * Finds a person by their first name and last name.
     *
     * @param firstName the first name of the person.
     * @param lastName the last name of the person.
     * @return an `Optional` containing the found `Person`, or empty if no match is found.
     */
    Optional<Person> findPerson(String firstName, String lastName);

    /**
     * Updates the details of an existing person in the repository.
     *
     * @param person the Person with updated information.
     */
    void update(Person person);

    /**
     * Saves a new person to the repository.
     *
     * @param person the Person to be saved.
     */
    void save(Person person);

    /**
     * Deletes a person from the repository.
     *
     * @param person the Person to be deleted.
     */
    void delete(Person person);

    /**
     * Retrieves all persons residing at the specified address.
     *
     * @param address the address to query.
     * @return a list of Person residing at the specified address.
     */
    List<Person> findPersonsByAddress(String address);

    /**
     * Retrieves all persons residing in the specified city.
     *
     * @param city the city to query.
     * @return a list of Person residing in the specified city.
     */
    List<Person> findPersonsByCity(String city);

    /**
     * Retrieves all persons with the specified last name.
     *
     * @param lastName the last name to query.
     * @return a list of Person with the specified last name.
     */
    List<Person> findPersonsByLastName(String lastName);
}
