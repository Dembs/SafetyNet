package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommunityEmailService {

    @Autowired
    private PersonRepository personRepository;

    public List<String> getEmailsByCity(String city) {
        log.info("Fetching emails for city: {}", city);

        List<Person> personsInCity = personRepository.findPersonsByCity(city);

        log.debug("Found {} persons in city: {}", personsInCity.size(), city);

        List<String> emails = personsInCity.stream()
                                           .map(Person::getEmail)
                                           .distinct()
                                           .toList();

        log.info("Retrieved {} unique emails for city: {}", emails.size(), city);
        return emails;
    }
}
