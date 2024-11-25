package com.safetynet.alerts.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class DateService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /**
     * Calculates the age based on a given birthdate and today's date.
     *
     * @param birthdate the birthdate in the format MM/dd/yyyy
     * @return the calculated age
     */
    public int calculateAge(String birthdate) {
        LocalDate birthDate = LocalDate.parse(birthdate, DATE_FORMATTER);
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        log.debug("Calculated age {} for birthdate: {}", age, birthdate);
        return age;
    }
}
