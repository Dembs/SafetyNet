package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllPersonsTest() throws Exception {

        List<Person> persons = List.of(new Person("John", "Doe", "1509 Culver St", "Culver", 90230, "john.doe@email.com", "841-874-6512"));
        when(personRepository.findAll()).thenReturn(persons);

        mockMvc.perform(get("/person"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.length()").value(1))
               .andExpect(jsonPath("$[0].firstName").value("John"))
               .andExpect(jsonPath("$[0].lastName").value("Doe"));

        verify(personRepository, times(1)).findAll();
    }


    @Test
    void addOnePersonTest() throws Exception {
        Person person = new Person("Jane", "Smith", "29 15th St", "Culver", 90230, "jane.smith@email.com", "841-874-6513");
        doNothing().when(personRepository).save(person);

        mockMvc.perform(post("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(person)))
               .andExpect(status().isOk())
               .andExpect(content().string("Person added successfully!"));

        verify(personRepository, times(1)).save(Mockito.any(Person.class));
    }

    @Test
    void addOnePersonTest_Exception() throws Exception {
        Person person = new Person("Jane", "Smith", "29 15th St", "Culver", 90230, "jane.smith@email.com", "841-874-6513");
        doThrow(new RuntimeException("Database error")).when(personRepository).save(Mockito.any(Person.class));

        mockMvc.perform(post("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(person)))
               .andExpect(status().isOk())
               .andExpect(content().string("Failed to add the person."));

        verify(personRepository, times(1)).save(Mockito.any(Person.class));
    }


    @Test
    void deleteOnePersonTest() throws Exception {
        Person person = new Person("Jane", "Smith", "29 15th St", "Culver", 90230, "jane.smith@email.com", "841-874-6513");
        when(personRepository.findPerson(person.getFirstName(), person.getLastName())).thenReturn(Optional.of(person));
        doNothing().when(personRepository).delete(person);

        mockMvc.perform(delete("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(person)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.firstName").value("Jane"))
               .andExpect(jsonPath("$.lastName").value("Smith"));

        verify(personRepository, times(1)).delete(person);
        verify(personRepository, times(1)).findPerson(person.getFirstName(), person.getLastName());
    }

    @Test
    void deleteOnePersonTest_PersonNotFound() throws Exception {
        Person person = new Person("Jane", "Smith", "29 15th St", "Culver", 90230, "jane.smith@email.com", "841-874-6513");
        when(personRepository.findPerson(person.getFirstName(), person.getLastName())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(person)))
               .andExpect(status().isOk())
               .andExpect(content().json("{}"));

        verify(personRepository, never()).delete(any(Person.class));
        verify(personRepository, times(1)).findPerson(person.getFirstName(), person.getLastName());
    }

    @Test
    void deleteOnePersonTest_Exception() throws Exception {
        Person person = new Person("Jane", "Smith", "29 15th St", "Culver", 90230, "jane.smith@email.com", "841-874-6513");
        when(personRepository.findPerson(person.getFirstName(), person.getLastName())).thenReturn(Optional.of(person));
        doThrow(new RuntimeException("Database error")).when(personRepository).delete(any(Person.class));

        mockMvc.perform(delete("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(person)))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Failed to delete the person."));

        verify(personRepository, times(1)).delete(any(Person.class));
        verify(personRepository, times(1)).findPerson(person.getFirstName(), person.getLastName());
    }

    @Test
    void updateOnePersonTest() throws Exception {
        Person person = new Person("Jane", "Smith", "29 15th St", "Culver", 90230, "jane.smith@email.com", "841-874-6513");
        when(personRepository.findPerson(person.getFirstName(), person.getLastName())).thenReturn(Optional.of(person));
        doNothing().when(personRepository).update(person);

        mockMvc.perform(put("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(person)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.firstName").value("Jane"))
               .andExpect(jsonPath("$.lastName").value("Smith"));

        verify(personRepository, times(1)).update(person);
        verify(personRepository, times(1)).findPerson(person.getFirstName(), person.getLastName());
    }

    @Test
    void updateOnePersonTest_PersonNotFound() throws Exception {
        Person person = new Person("Jane", "Smith", "29 15th St", "Culver", 90230, "jane.smith@email.com", "841-874-6513");
        when(personRepository.findPerson(person.getFirstName(), person.getLastName())).thenReturn(Optional.empty());

        mockMvc.perform(put("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(person)))
               .andExpect(status().isOk())
               .andExpect(content().json("{}"));

        verify(personRepository, never()).update(any(Person.class));
        verify(personRepository, times(1)).findPerson(person.getFirstName(), person.getLastName());
    }

    @Test
    void updateOnePersonTest_Exception() throws Exception {
        Person person = new Person("Jane", "Smith", "29 15th St", "Culver", 90230, "jane.smith@email.com", "841-874-6513");
        when(personRepository.findPerson(person.getFirstName(), person.getLastName())).thenReturn(Optional.of(person));
        doThrow(new RuntimeException("Database error")).when(personRepository).update(any(Person.class));

        mockMvc.perform(put("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(person)))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Failed to update the person."));

        verify(personRepository, times(1)).update(any(Person.class));
        verify(personRepository, times(1)).findPerson(person.getFirstName(), person.getLastName());
    }
}
