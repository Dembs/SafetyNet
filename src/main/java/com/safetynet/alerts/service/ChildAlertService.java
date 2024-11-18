package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildInfoDTO;
import com.safetynet.alerts.dto.ChildInfoDTO.FamilyMember;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChildAlertService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<ChildInfoDTO> getChildrenAtAddress(String address) {
        List<Person> personsAtAddress = personRepository.findPersonsByAddress(address);

        List<ChildInfoDTO> children = new ArrayList<>();

        for (Person person : personsAtAddress) {
            Optional<MedicalRecord> medicalRecordPers = medicalRecordRepository.findMedicalRecord(person.getFirstName(), person.getLastName());

            if (medicalRecordPers.isPresent()) {
                MedicalRecord medicalRecord = medicalRecordPers.get();
                int age = calculateAge(medicalRecord.getBirthdate());

                if (age <= 18) {
                    List<FamilyMember> familyMembers = new ArrayList<>();
                    for (Person member : personsAtAddress) {
                        if (!member.getFirstName().equals(person.getFirstName()) || !member.getLastName().equals(person.getLastName())) {
                            familyMembers.add(new FamilyMember(member.getFirstName(), member.getLastName()));
                        }
                    }
                    children.add(new ChildInfoDTO(person.getFirstName(), person.getLastName(), age, familyMembers));
                }
            }
        }

        return children;
    }

    private int calculateAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
