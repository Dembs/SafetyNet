package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FireStationService {
    @Autowired
    FireStationRepository fireStationRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    public Map<String, Object> getPersonsByStation(String stationNumber) {

        List<String> addresses = fireStationRepository.findAddressByStationNumber(stationNumber)
                                                      .orElseThrow(() -> new IllegalArgumentException("Invalid station number"));


        List<Person> persons = addresses.stream()
                                        .flatMap(address -> personRepository.findPersonsByAddress(address).stream())
                                        .toList() ;


        List<PersonInfoDTO> personsInfo = new ArrayList<>();
        int adultCount = 0;
        int childCount = 0;

        for (Person person : persons) {

            int age = medicalRecordRepository.findMedicalRecord(person.getFirstName(), person.getLastName())
                                             .map(record -> calculateAge(record.getBirthdate()))
                                             .orElse(0);

            personsInfo.add(new PersonInfoDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()));


            if (age <= 18) {
                childCount++;
            } else {
                adultCount++;
            }
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("adults", adultCount);
        result.put("children", childCount);
        result.put("persons", personsInfo);

        return result;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
