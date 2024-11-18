package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ResidentInfoDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FireInfoService {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public Map<String, Object> getResidentsByAddress(String address) {

        String fireStationNumber = fireStationRepository.findAll().stream()
                                                        .filter(fireStation -> fireStation.getAddress().equals(address))
                                                        .map(fireStation -> fireStation.getStation())
                                                        .findFirst()
                                                        .orElseThrow(() -> new IllegalArgumentException("No fire station found for the address"));

        // Find residents at the address
        List<Person> residents = personRepository.findPersonsByAddress(address);

        List<ResidentInfoDTO> residentInfoList = residents.stream().map(person -> {
            Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findMedicalRecord(person.getFirstName(), person.getLastName());
            int age = medicalRecord.map(record -> calculateAge(record.getBirthdate())).orElse(0);
            List<String> medications = medicalRecord.map(MedicalRecord::getMedications).orElse(List.of());
            List<String> allergies = medicalRecord.map(MedicalRecord::getAllergies).orElse(List.of());

            return new ResidentInfoDTO(person.getFirstName(), person.getLastName(), person.getPhone(), age, medications, allergies);
        }).toList();

        return Map.of(
                "fireStationNumber", fireStationNumber,
                "residents", residentInfoList
        );
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
