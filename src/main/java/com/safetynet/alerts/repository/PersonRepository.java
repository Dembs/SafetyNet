package com.safetynet.alerts.repository;


import com.safetynet.alerts.model.Person;
import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    List<Person> findAll();
    Optional<Person> findPerson(String firstName, String lastName);
    void update(Person person);
    void save(Person person);
    void delete(Person person);
    List<Person> findPersonsByAddress(String address);

    List<Person> findPersonsByCity(String city);

    List<Person> findPersonsByLastName(String lastName);
}
