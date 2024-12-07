package com.safetynet.alerts.service;

import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for fetching phone numbers of residents covered by a specific fire station.
 */
@Slf4j
@Service
public class PhoneAlertService {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonRepository personRepository;

    /**
     * Retrieves a list of phone numbers of all residents covered by the specified fire station.
     */
    public List<String> getPhoneNumbersByFireStation(String fireStationNumber) {
        log.info("Fetching phone numbers for fire station number: {}", fireStationNumber);

        List<String> addresses = fireStationRepository.findAddressByStationNumber(fireStationNumber)
                                                      .orElseThrow(() -> {
                                                          log.error("Invalid fire station number: {}", fireStationNumber);
                                                          return new IllegalArgumentException("Invalid fire station number");
                                                      });

        log.debug("Found {} addresses for fire station number {}: {}", addresses.size(), fireStationNumber, addresses);

        List<String> phoneNumbers = addresses.stream()
                                             .flatMap(address -> {
                                                 log.debug("Fetching persons at address: {}", address);
                                                 return personRepository.findPersonsByAddress(address).stream();
                                             })
                                             .map(person -> {
                                                 log.debug("Extracting phone number for person: {}", person);
                                                 return person.getPhone();
                                             })
                                             .toList();

        log.info("Retrieved {} phone numbers for fire station number {}", phoneNumbers.size(), fireStationNumber);
        return phoneNumbers;
    }
}
