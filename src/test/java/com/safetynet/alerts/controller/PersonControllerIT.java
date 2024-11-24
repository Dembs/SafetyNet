package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeAll
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getAllPersonsIT() throws Exception {
        mockMvc.perform(get("/person")
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", not(empty())))
               .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @Test
    void addOnePersonIT() throws Exception {
        Person person = new Person("TestFirstName", "TestLastName", "123 Test St", "TestCity", 12345, "123-456-7890", "test@example.com");
        String personJson = """
                {
                    "firstName": "TestFirstName",
                    "lastName": "TestLastName",
                    "address": "123 Test St",
                    "city": "TestCity",
                    "zip": 12345,
                    "phone": "123-456-7890",
                    "email": "test@example.com"
                }
                """;

        mockMvc.perform(post("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(personJson))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("Person added successfully!")));
    }

    @Test
    void updateOnePersonIT() throws Exception {
        Person updatedPerson = new Person("UpdatedFirstName", "UpdatedLastName", "456 Updated St", "UpdatedCity", 54321, "987-654-3210", "updated@example.com");
        String updatedPersonJson = """
                {
                    "firstName": "UpdatedFirstName",
                    "lastName": "UpdatedLastName",
                    "address": "456 Updated St",
                    "city": "UpdatedCity",
                    "zip": 54321,
                    "phone": "987-654-3210",
                    "email": "updated@example.com"
                }
                """;

        mockMvc.perform(put("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(updatedPersonJson))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("Person updated successfully")));
    }

    @Test
    void deleteOnePersonIT() throws Exception {
        Person personToDelete = new Person("DeleteFirstName", "DeleteLastName", "789 Delete St", "DeleteCity", 98765, "321-654-0987", "delete@example.com");
        String personToDeleteJson = """
                {
                    "firstName": "DeleteFirstName",
                    "lastName": "DeleteLastName",
                    "address": "789 Delete St",
                    "city": "DeleteCity",
                    "zip": 98765,
                    "phone": "321-654-0987",
                    "email": "delete@example.com"
                }
                """;

        mockMvc.perform(delete("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(personToDeleteJson))
               .andExpect(status().isOk())
               .andExpect(content().string(containsString("Person deleted successfully")));
    }
}
