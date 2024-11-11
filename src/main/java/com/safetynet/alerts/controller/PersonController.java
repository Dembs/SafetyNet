package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Endpoint to get all persons
    @GetMapping
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    //Endpoint to add one person
    @PostMapping
    public String addOnePerson(@RequestBody Person person){
         personRepository.save(person);
         return "Person added successfully!";
    }

    //Endpoint to delete one person
    @DeleteMapping
    public String deleteOnePerson(@RequestBody Person person){
        personRepository.delete(person);
        return "Person deleted successfully";
    }

    //Endpoint to update one person
    @PutMapping
    public String updateOnePerson(@RequestBody Person person){
        personRepository.update(person);
        return  "Person updated successfully";
    }
}
