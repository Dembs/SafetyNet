package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.DataLoader;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl implements PersonRepository {
    private List<Person> persons;

    public PersonRepositoryImpl(DataLoader dataLoader) {
        this.persons = new ArrayList<>(dataLoader.getData().getPersons());
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(persons);
    }

    @Override
    public Optional<Person> findPerson(String firstName, String lastName) {
        return persons.stream()
                      .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                      .findFirst();
    }

    @Override
    public void save(Person person) {
        persons.add(person);
    }

    @Override
    public void delete(Person deletedPerson) {
        findPerson(deletedPerson.getFirstName(), deletedPerson.getLastName())
                .ifPresent(person -> persons.remove(person));
    }
    @Override
    public void update(Person updatedPerson) {
        findPerson(updatedPerson.getFirstName(), updatedPerson.getLastName())
                .ifPresent(personToUpdate -> {

                    personToUpdate.setAddress(updatedPerson.getAddress());
                    personToUpdate.setCity(updatedPerson.getCity());
                    personToUpdate.setZip(updatedPerson.getZip());
                    personToUpdate.setPhone(updatedPerson.getPhone());
                    personToUpdate.setEmail(updatedPerson.getEmail());
                });
    }
}
