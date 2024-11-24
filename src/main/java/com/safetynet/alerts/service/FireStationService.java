package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireStationInfoDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class FireStationService {
    @Autowired
    FireStationRepository fireStationRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    public Map<String, Object> getPersonsByStation(String stationNumber) {
        log.info("Fetching persons for station number: {}", stationNumber);

        List<String> addresses = fireStationRepository.findAddressByStationNumber(stationNumber)
                                                      .orElseThrow(() -> {
                                                          log.error("Invalid station number: {}", stationNumber);
                                                          return new IllegalArgumentException("Invalid station number");
                                                      });

        log.debug("Addresses covered by station {}: {}", stationNumber, addresses);

        List<Person> persons = addresses.stream()
                                        .flatMap(address -> {
                                            log.debug("Fetching persons at address: {}", address);
                                            return personRepository.findPersonsByAddress(address).stream();
                                        })
                                        .toList();

        List<FireStationInfoDTO> personsInfo = new ArrayList<>();
        int adultCount = 0;
        int childCount = 0;

        for (Person person : persons) {
            int age = medicalRecordRepository.findMedicalRecord(person.getFirstName(), person.getLastName())
                                             .map(record -> calculateAge(record.getBirthdate()))
                                             .orElse(0);

            log.debug("Person: {} {}, Age: {}", person.getFirstName(), person.getLastName(), age);

            personsInfo.add(new FireStationInfoDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()));

            if (age <= 18) {
                childCount++;
            } else {
                adultCount++;
            }
        }

        log.info("Processed {} adults and {} children for station number {}", adultCount, childCount, stationNumber);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("adults", adultCount);
        result.put("children", childCount);
        result.put("persons", personsInfo);

        return result;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        log.debug("Calculated age {} for birthdate: {}", age, birthdate);
        return age;
    }
}
