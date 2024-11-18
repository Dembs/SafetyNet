package com.safetynet.alerts.service;

import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneAlertService {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonRepository personRepository;

    public List<String> getPhoneNumbersByFireStation(String fireStationNumber) {

        List<String> addresses = fireStationRepository.findAddressByStationNumber(fireStationNumber)
                                                      .orElseThrow(() -> new IllegalArgumentException("Invalid fire station number"));

        return addresses.stream()
                        .flatMap(address -> personRepository.findPersonsByAddress(address).stream())
                        .map(person -> person.getPhone())
                        .toList();
    }
}
