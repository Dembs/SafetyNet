package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for retrieving medical records, age, medications, and allergies about persons based on their last name.
 */
@Slf4j
@Service
public class PersonInfoService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private DateService dateService;

    /**
     * Retrieves a list of detailed information about persons with the specified last name.
     */
    public List<PersonInfoDTO> getPersonsByLastName(String lastName) {
        log.info("Fetching persons with last name: {}", lastName);

        List<Person> persons = personRepository.findPersonsByLastName(lastName);
        log.debug("Found {} persons with last name: {}", persons.size(), lastName);

        List<PersonInfoDTO> personInfoList = persons.stream()
                                                    .map(this::buildResidentInfo)
                                                    .toList();

        log.info("Successfully built PersonInfoDTO list for last name: {}", lastName);
        return personInfoList;
    }

    /**
     * Builds additional details from medical records for a given Person
     *
     */
    private PersonInfoDTO buildResidentInfo(Person person) {
        log.debug("Building PersonInfoDTO for person: {} {}", person.getFirstName(), person.getLastName());

        Optional<MedicalRecord> medicalRecordOpt = medicalRecordRepository.findMedicalRecord(person.getFirstName(), person.getLastName());
        int age = medicalRecordOpt.map(record -> dateService.calculateAge(record.getBirthdate())).orElse(0);
        List<String> medications = medicalRecordOpt.map(MedicalRecord::getMedications).orElse(List.of());
        List<String> allergies = medicalRecordOpt.map(MedicalRecord::getAllergies).orElse(List.of());

        log.debug("Resident: {} {}, Age: {}, Medications: {}, Allergies: {}",
                person.getFirstName(), person.getLastName(), age, medications, allergies);

        return new PersonInfoDTO(
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getEmail(),
                age,
                medications,
                allergies
        );
    }
}
