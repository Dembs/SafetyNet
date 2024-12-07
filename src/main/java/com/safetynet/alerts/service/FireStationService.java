package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireStationInfoDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service responsible for retrieving information about persons covered by specific fire stations.
 * It also includes counts of adults and children.
 */
@Slf4j
@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private DateService dateService;

    /**
     * Retrieves a list of persons covered by the specified fire station number.
     */
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

        // Process each person to classify as adult/child
        for (Person person : persons) {
            int age = medicalRecordRepository.findMedicalRecord(person.getFirstName(), person.getLastName())
                                             .map(record -> dateService.calculateAge(record.getBirthdate()))
                                             .orElse(0);

            log.debug("Person: {} {}, Age: {}", person.getFirstName(), person.getLastName(), age);

            personsInfo.add(new FireStationInfoDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()));

            // Update adult and child counts based on age
            if (age <= 18) {
                childCount++;
            } else {
                adultCount++;
            }
        }

        log.info("Processed {} adults and {} children for station number {}", adultCount, childCount, stationNumber);

        // Construct the response map
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("adults", adultCount);
        result.put("children", childCount);
        result.put("persons", personsInfo);

        return result;
    }

}
