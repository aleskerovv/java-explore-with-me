package ru.practicum.ewn.service.utils;

import lombok.experimental.UtilityClass;
import ru.practicum.ewn.service.enums.EventState;
import ru.practicum.ewn.service.enums.StateAction;
import ru.practicum.ewn.service.events.model.Event;
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

    public static void checkActionState(StateAction stateAction, Event event) {

        switch (stateAction) {
            case PUBLISH_EVENT:
                event.setPublishedOn(LocalDateTime.now());
                event.setEventState(EventState.PUBLISHED);
                break;
            case REJECT_EVENT:
            case CANCEL_REVIEW:
                event.setEventState(EventState.CANCELED);
                break;
            case SEND_TO_REVIEW:
                event.setEventState(EventState.PENDING);
                break;
            default:
                throw new DataValidationException(String.format("Incorrect state action: %s", stateAction));
        }
    }
}
