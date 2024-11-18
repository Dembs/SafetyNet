package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.PhoneAlertService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneAlertController.class)
class PhoneAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneAlertService phoneAlertService;


    @Test
    void getPhoneNumbers() throws Exception {
        // Mock data
        String fireStationNumber = "1";
        List<String> phoneNumbers = List.of("123-456-7890", "987-654-3210");

        // Mock service call
        when(phoneAlertService.getPhoneNumbersByFireStation(fireStationNumber)).thenReturn(phoneNumbers);

        // Perform request and verify response
        mockMvc.perform(get("/phoneAlert")
                       .param("firestation", fireStationNumber))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0]").value("123-456-7890"))
               .andExpect(jsonPath("$[1]").value("987-654-3210"));
    }

}
