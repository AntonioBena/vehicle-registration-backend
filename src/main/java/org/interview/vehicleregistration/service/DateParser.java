package org.interview.vehicleregistration.service;

import lombok.extern.slf4j.Slf4j;
import org.interview.vehicleregistration.configuration.ApplicationProperties;
import org.interview.vehicleregistration.exception.custom.DateParseException;

import java.time.*;

@Slf4j
public class DateParser {

    private DateParser(ApplicationProperties appProperties) {
    }

    public static LocalDate parseDate(String toParse) {
        try {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(toParse);
            return offsetDateTime.toLocalDate(); //Invalid date format : 2025-03-13T23:00:00.000Z , Text '2025-03-13T23:00:00.000Z' could not be parsed, unparsed text found at index 10
        } catch (Exception ex) {
            log.debug("Can not parse: {}", toParse);
            throw new DateParseException("Invalid date format : " + toParse + " , " + ex.getMessage());
        }
    }
}