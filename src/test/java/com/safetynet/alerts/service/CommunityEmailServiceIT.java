package com.safetynet.alerts.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommunityEmailServiceIT {

    @Autowired
    private CommunityEmailService communityEmailService;

    @Autowired
    private DataLoader dataLoader;

    @BeforeAll
    void setupRealData() {
        dataLoader.loadData();
    }

    @Test
    void getEmailsByCity_ShouldReturnEmailsForCity() {

        String city = "Culver";

        List<String> emails = communityEmailService.getEmailsByCity(city);

        assertTrue(emails.size() > 0, "The email list should not be empty");
        assertTrue(emails.stream().distinct().count() == emails.size(), "All emails should be unique");

        System.out.println("Retrieved emails: " + emails);
    }
}
