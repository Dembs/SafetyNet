package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfoDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonInfoServiceIT {

    @Autowired
    private PersonInfoService personInfoService;

    @Autowired
    private DataLoader dataLoader;

    @BeforeAll
    void setupRealData() {
        dataLoader.loadData();
    }

    @Test
    void getPersonsByLastNameIT() {
        String testLastName = "Boyd";

        List<PersonInfoDTO> personInfoList = personInfoService.getPersonsByLastName(testLastName);

        assertNotNull(personInfoList, "PersonInfoDTO list should not be null");
        assertFalse(personInfoList.isEmpty(), "PersonInfoDTO list should not be empty");

    }
}
