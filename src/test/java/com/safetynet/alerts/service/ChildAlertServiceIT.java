package com.safetynet.alerts.service;
import com.safetynet.alerts.dto.ChildInfoDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChildAlertServiceIT {

    @Autowired
    private ChildAlertService childAlertService;

    @Autowired
    private DataLoader dataLoader;

    @BeforeAll
    void setupRealData() {
        dataLoader.loadData();
    }

    @Test
    void getChildrenAtAddressIT() {

        String testAddress = "1509 Culver St";

        List<ChildInfoDTO> children = childAlertService.getChildrenAtAddress(testAddress);

        assertNotNull(children, "Children list should not be null");
        assertFalse(children.isEmpty(), "Children list should not be empty");

        assertTrue(children.get(0).getAge() <18,"Child age should be less than 18");
        assertNotNull(children.get(0).getFamilyMembers(), "Family members list should not be null");


    }
}
