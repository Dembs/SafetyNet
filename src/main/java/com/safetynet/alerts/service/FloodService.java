package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ResidentInfoDTO;
import com.safetynet.alerts.model.MedicalRecord;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class FloodService {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public Map<String, List<ResidentInfoDTO>> getHouseholdsByStations(List<String> stationNumbers) {
        log.info("Fetching households for fire stations: {}", stationNumbers);

        Set<String> addresses = stationNumbers.stream()
                                              .flatMap(station -> {
                                                  log.debug("Finding addresses for station number: {}", station);
                                                  return fireStationRepository.findAddressByStationNumber(station).stream();
                                              })
                                              .flatMap(Collection::stream)
                                              .collect(Collectors.toSet());

        log.debug("Addresses found for stations {}: {}", stationNumbers, addresses);

        Map<String, List<ResidentInfoDTO>> households = new LinkedHashMap<>();

        for (String address : addresses) {
            log.debug("Processing residents for address: {}", address);
            List<Person> residents = personRepository.findPersonsByAddress(address);
            List<ResidentInfoDTO> residentInfos = residents.stream()
                                                           .map(person -> buildResidentInfo(person))
                                                           .toList();

            households.put(address, residentInfos);
            log.debug("Processed household at {}: {} residents", address, residentInfos.size());
        }

        log.info("Successfully processed {} households for fire stations {}", households.size(), stationNumbers);
        return households;
    }

    private ResidentInfoDTO buildResidentInfo(Person person) {
        log.debug("Building ResidentInfoDTO for person: {} {}", person.getFirstName(), person.getLastName());
        Optional<MedicalRecord> medicalRecordOpt = medicalRecordRepository.findMedicalRecord(person.getFirstName(), person.getLastName());
        int age = medicalRecordOpt.map(record -> calculateAge(record.getBirthdate())).orElse(0);
        List<String> medications = medicalRecordOpt.map(MedicalRecord::getMedications).orElse(List.of());
        List<String> allergies = medicalRecordOpt.map(MedicalRecord::getAllergies).orElse(List.of());

        log.debug("Resident: {} {}, Age: {}, Medications: {}, Allergies: {}",
                person.getFirstName(), person.getLastName(), age, medications, allergies);

        return new ResidentInfoDTO(person.getFirstName(), person.getLastName(), person.getPhone(), age, medications, allergies);
    }

    private int calculateAge(String birthdate) {
        log.debug("Calculating age for birthdate: {}", birthdate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        log.debug("Calculated age: {}", age);
        return age;
    }
}
