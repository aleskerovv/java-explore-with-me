package ru.practicum.ewn.service.utils;

import lombok.experimental.UtilityClass;
import ru.practicum.ewn.service.handlers.DataValidationException;

import java.time.LocalDateTime;

@UtilityClass
public class DataChecker {
    public static void dateTimeChecker(LocalDateTime dateTime) {
        if (dateTime != null) {
            final int timeDelta = 2;
            if (dateTime.isBefore(LocalDateTime.now().plusHours(timeDelta))) {
                throw new DataValidationException(String.format("Event date can not be earlier then %s hours from now time",
                        timeDelta));
            }
        }
    }
}
