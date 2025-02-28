package org.interview.vehicleregistration.service;

import lombok.extern.slf4j.Slf4j;
import org.interview.vehicleregistration.configuration.ApplicationProperties;
import org.interview.vehicleregistration.exception.custom.DateParseException;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateParser {

    private static String formatPattern = "";

    private DateParser(ApplicationProperties appProperties) {
        formatPattern = appProperties.getDatePattern();
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);

    public static String covertDateToString(LocalDate date) {
        return date.format(formatter);
    }

    public static LocalDate parseDate(String toParse) {
        try {
            return LocalDate.parse(toParse, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception ex) {
            log.debug("Can not parse: {}", toParse);
            throw new DateParseException("Invalid date format : " + toParse + " , " + ex.getMessage());
        }
    }
}