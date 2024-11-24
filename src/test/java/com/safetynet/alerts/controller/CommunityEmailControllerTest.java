package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.CommunityEmailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommunityEmailController.class)
class CommunityEmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommunityEmailService communityEmailService;

    @Test
    void getEmailsByCityTest() throws Exception {
        String city = "Culver";
        List<String> mockEmails = List.of("john.doe@example.com", "jane.doe@example.com");

        when(communityEmailService.getEmailsByCity(city)).thenReturn(mockEmails);

        mockMvc.perform(get("/communityEmail").param("city", city))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(mockEmails.size()))
               .andExpect(jsonPath("$[0]").value("john.doe@example.com"))
               .andExpect(jsonPath("$[1]").value("jane.doe@example.com"));
    }


}
