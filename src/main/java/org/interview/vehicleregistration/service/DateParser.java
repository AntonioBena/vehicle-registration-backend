package org.interview.vehicleregistration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.interview.vehicleregistration.exception.custom.DateParseException;

import java.time.*;

@Log4j2
@RequiredArgsConstructor
public abstract class DateParser {

    public static LocalDate parseDate(String dateToParse) {
        try {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateToParse);
            return offsetDateTime.toLocalDate();
        } catch (Exception ex) {
            log.debug("Can not parse: {}", dateToParse);
            throw new DateParseException("Invalid date format : " + dateToParse + " , " + ex.getMessage());
        }
    }
}