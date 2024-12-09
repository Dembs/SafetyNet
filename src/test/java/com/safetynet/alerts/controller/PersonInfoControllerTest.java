package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.service.PersonInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonInfoController.class)
class PersonInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonInfoService personInfoService;

    @Test
    void getPersonsByLastNameTest() throws Exception {

        String lastName = "Doe";
        List<PersonInfoDTO> mockResponse = List.of(
                new PersonInfoDTO("John", "Doe", "1509 Culver St", "jaboyd@email.com", 30, List.of("med1_1","med1_2"), List.of("allergy1")),
                new PersonInfoDTO("Jane", "Doe", "123 Main St", "jane.doe@example.com", 25, List.of("med2"), List.of("allergy2"))
        );

        when(personInfoService.getPersonsByLastName(lastName)).thenReturn(mockResponse);

        mockMvc.perform(get("/personInfo").param("lastName", lastName))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].firstName").value("John"))
               .andExpect(jsonPath("$[0].email").value("jaboyd@email.com"))
               .andExpect(jsonPath("$[0].age").value(30))
               .andExpect(jsonPath("$[1].firstName").value("Jane"))
               .andExpect(jsonPath("$[1].email").value("jane.doe@example.com"))
               .andExpect(jsonPath("$[1].age").value(25));
    }

    @Test
    void getPersonsByLastName_EmptyResponseTest() throws Exception {
        String lastName = "123 test";
        when(personInfoService.getPersonsByLastName(lastName)).thenReturn(List.of());

        mockMvc.perform(get("/personInfo").param("lastName", lastName))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isEmpty());
    }

}
