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
    void getAllPersons() throws Exception {

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
    void addOnePerson() throws Exception {
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
    void deleteOnePerson() throws Exception {
        Person person = new Person("Jane", "Smith", "29 15th St", "Culver", 90230, "jane.smith@email.com", "841-874-6513");
        doNothing().when(personRepository).delete(person);

        mockMvc.perform(delete("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(person)))
               .andExpect(status().isOk())
               .andExpect(content().string("Person deleted successfully"));

        verify(personRepository, times(1)).delete(Mockito.any(Person.class));
    }

    @Test
    void updateOnePerson() throws Exception {

        Person person = new Person("Jane", "Smith", "29 15th St", "Culver", 90230, "jane.smith@email.com", "841-874-6513");
        doNothing().when(personRepository).update(person);

        mockMvc.perform(put("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(person)))
               .andExpect(status().isOk())
               .andExpect(content().string("Person updated successfully"));

        verify(personRepository, times(1)).update(Mockito.any(Person.class));
    }
}
