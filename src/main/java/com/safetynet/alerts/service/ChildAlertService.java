package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildInfoDTO;
import com.safetynet.alerts.dto.ChildInfoDTO.FamilyMember;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service to retrieve information about children and their family members at a given address.
 */
@Slf4j
@Service
public class ChildAlertService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private DateService dateService;

    public List<ChildInfoDTO> getChildrenAtAddress(String address) {
        log.info("Fetching children at address: {}", address);

        List<Person> personsAtAddress = personRepository.findPersonsByAddress(address);
        log.debug("Found {} persons at address: {}", personsAtAddress.size(), address);

        List<ChildInfoDTO> children = new ArrayList<>();

        for (Person person : personsAtAddress) {
            log.debug("Processing person: {} {}", person.getFirstName(), person.getLastName());

            Optional<MedicalRecord> medicalRecordPers = medicalRecordRepository.findMedicalRecord(person.getFirstName(), person.getLastName());

            if (medicalRecordPers.isPresent()) {
                MedicalRecord medicalRecord = medicalRecordPers.get();
                int age = dateService.calculateAge(medicalRecord.getBirthdate());
                log.debug("Calculated age for {} {}: {}", person.getFirstName(), person.getLastName(), age);

                if (age <= 18) {
                    log.debug("Person {} {} is a child (age: {})", person.getFirstName(), person.getLastName(), age);
                    List<FamilyMember> familyMembers = new ArrayList<>();
                    for (Person member : personsAtAddress) {
                        if (!member.getFirstName().equals(person.getFirstName()) || !member.getLastName().equals(person.getLastName())) {
                            familyMembers.add(new FamilyMember(member.getFirstName(), member.getLastName()));
                        }
                    }

                    log.debug("Found {} family members for child {} {}", familyMembers.size(), person.getFirstName(), person.getLastName());
                    children.add(new ChildInfoDTO(person.getFirstName(), person.getLastName(), age, familyMembers));
                }
            }
        }

        log.info("Found {} children at address: {}", children.size(), address);
        return children;
    }

}
