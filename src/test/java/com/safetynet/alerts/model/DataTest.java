package com.safetynet.alerts.model;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataTest {

    private Data data;
    private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalRecords;

    @Test
    public void getDataTest(){
        persons = new ArrayList<>();
        firestations = new ArrayList<>();
        medicalRecords = new ArrayList<>();
        List<String> medications = Arrays.asList("aznol:350mg", "hydrapermazol:100mg");
        List<String> allergies = Arrays.asList("nillacilan");

        persons.add(new Person("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"));
        firestations.add(new FireStation("1509 Culver St", "3"));
        medicalRecords.add(new MedicalRecord("John", "Boyd", "03/06/1984", medications, allergies));

        data = new Data(persons, firestations, medicalRecords);

        assertEquals("John", data.getPersons().get(0).getFirstName());
        assertEquals("1509 Culver St", data.getFirestations().get(0).getAddress() );
        assertEquals("John", data.getMedicalrecords().get(0).getFirstName());
    }
}
