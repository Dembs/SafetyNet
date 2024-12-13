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
        String updatedPerson = """
                {
                    "firstName": "Test",
                    "lastName": "Test",
                    "address": "1509 Culver St",
                    "city": "Culver",
                    "zip": "97451",
                    "phone": "841-874-6512",
                    "email": "jaboyd@email.com"
                }
                """;

        mockMvc.perform(put("/person")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(updatedPerson))
               .andExpect(status().isOk())
               .andExpect(content().string("{}"));
    }

    @Test
    void deleteOnePersonIT() throws Exception {
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
               .andExpect(content().string("{}"));
    }
}
