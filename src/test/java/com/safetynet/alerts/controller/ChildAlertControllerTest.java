package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildInfoDTO;
import com.safetynet.alerts.dto.ChildInfoDTO.FamilyMember;
import com.safetynet.alerts.service.ChildAlertService;
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

@WebMvcTest(ChildAlertController.class)
class ChildAlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChildAlertService childAlertService;

    @Test
    void getChildrenAtAddress() throws Exception {
        List<FamilyMember> familyMembers = List.of(new FamilyMember("John", "Doe"));
        List<ChildInfoDTO> children = List.of(
                new ChildInfoDTO("Jane", "Doe", 10, familyMembers)
        );

        String address = "123 Exemple St";
        when(childAlertService.getChildrenAtAddress(address)).thenReturn(children);

        mockMvc.perform(get("/childAlert")
                       .param("address", address))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].firstName").value("Jane"))
               .andExpect(jsonPath("$[0].lastName").value("Doe"))
               .andExpect(jsonPath("$[0].age").value(10))
               .andExpect(jsonPath("$[0].familyMembers[0].firstName").value("John"))
               .andExpect(jsonPath("$[0].familyMembers[0].lastName").value("Doe"));
    }
    @Test
    void getChildrenAtAddress_EmptyResponseTest() throws Exception {
        String address = "123 test";
        when(childAlertService.getChildrenAtAddress(address)).thenReturn(List.of());

        mockMvc.perform(get("/childAlert")
                       .param("address", address))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isEmpty());
    }

}
