package org.interview.vehicleregistration.service;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

@Slf4j
public class DateParser {

    private DateParser() {}

    private static final List<DateTimeFormatter> inputFormatters = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),         // 2024-08-14
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),         // 14/08/2024
            DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)  // 14 August 2024
    );

    public static String covertDateToString(LocalDate date) {
        return date.format(inputFormatters.get(1));
    }

    public static LocalDate parseDate(String toParse){
        for (DateTimeFormatter formatter : inputFormatters) {
            try {
                return LocalDate.parse(toParse, formatter);
            } catch (DateTimeParseException ignored) {
                log.debug("Ca not parse: {}",toParse);
            }
        }
        throw new IllegalArgumentException("Invalid date format: " + toParse); //TODO DateParseException
    }
}