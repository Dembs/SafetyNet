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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class FireInfoService {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public Map<String, Object> getResidentsByAddress(String address) {
        log.info("Fetching residents and fire station information for address: {}", address);

        try {
            log.debug("Looking up fire station number for address: {}", address);
            String fireStationNumber = fireStationRepository.findAll().stream()
                                                            .filter(fireStation -> fireStation.getAddress().equals(address))
                                                            .map(fireStation -> fireStation.getStation())
                                                            .findFirst()
                                                            .orElseThrow(() -> {
                                                                log.error("No fire station found for address: {}", address);
                                                                return new IllegalArgumentException("No fire station found for the address");
                                                            });

            log.debug("Fire station number found: {}", fireStationNumber);

            log.debug("Retrieving residents for address: {}", address);
            List<Person> residents = personRepository.findPersonsByAddress(address);

            log.debug("Constructing ResidentInfoDTO list");
            List<ResidentInfoDTO> residentInfoList = residents.stream().map(person -> {
                Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findMedicalRecord(person.getFirstName(), person.getLastName());
                int age = medicalRecord.map(record -> calculateAge(record.getBirthdate())).orElse(0);
                List<String> medications = medicalRecord.map(MedicalRecord::getMedications).orElse(List.of());
                List<String> allergies = medicalRecord.map(MedicalRecord::getAllergies).orElse(List.of());

                return new ResidentInfoDTO(person.getFirstName(), person.getLastName(), person.getPhone(), age, medications, allergies);
            }).toList();

            log.info("Successfully retrieved {} residents for address: {}", residentInfoList.size(), address);

            return Map.of(
                    "fireStationNumber", fireStationNumber,
                    "residents", residentInfoList
            );
        } catch (Exception e) {
            log.error("Error occurred while fetching data for address: {}", address, e);
            throw e;
        }
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        log.debug("Calculated age {} for birthdate: {}", age, birthdate);
        return age;
    }
}
