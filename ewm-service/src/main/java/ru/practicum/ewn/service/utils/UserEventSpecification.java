package ru.practicum.ewn.service.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewn.service.enums.EventState;
import ru.practicum.ewn.service.events.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@UtilityClass
public class UserEventSpecification {
    public static Specification<Event> filtersFromUser(UserEventFilter eventFilter) {
        return (stateEquals()).and(categoriesFilter(eventFilter.getCategories()))
                .and(paidFilter(eventFilter.getPaid()))
                .and(dateFilter(eventFilter.getRangeStart(), eventFilter.getRangeEnd()))
                .and(onlyAvailableFilter(eventFilter.getOnlyAvailable()))
                .and(annotationFilter(eventFilter.getText()).or(descriptionFilter(eventFilter.getText())));

    }

    private static Specification<Event> annotationFilter(final String text) {
        return (root, query, cb) ->
                text != null && !text.isBlank()
                        ? cb.like(cb.lower(root.get("annotation")), cb.lower(cb.literal(text)))
                        : cb.conjunction();
    }

    private static Specification<Event> descriptionFilter(final String text) {
        return (root, query, cb) ->
                text != null && !text.isBlank()
                        ? cb.like(cb.lower(root.get("description")), cb.lower(cb.literal(text)))
                        : cb.conjunction();
    }

    private static Specification<Event> dateFilter(final LocalDateTime start,
                                                   final LocalDateTime end) {
        return ((root, query, cb) ->
                start == null && end == null
                        ? cb.greaterThanOrEqualTo(root.get("eventDate"), LocalDateTime.now())
                        : cb.between(root.get("eventDate"), start, end));
    }

    private static Specification<Event> paidFilter(final Boolean paid) {
        return ((root, query, cb) ->
                paid == null
                        ? cb.conjunction()
                        : cb.equal(root.get("paid"), paid));
    }

    private static Specification<Event> categoriesFilter(final List<Long> categoriesIds) {
        return ((root, query, cb) ->
                categoriesIds != null
                        ? cb.in(root.get("category").get("id")).value(categoriesIds)
                        : cb.conjunction());
    }

    private static Specification<Event> onlyAvailableFilter(final Boolean onlyAvailable) {
        return ((root, query, cb) ->
                onlyAvailable != null
                        ? cb.or(cb.equal(root.get("participantLimit"), 0),
                        cb.greaterThanOrEqualTo(root.get("participantLimit"),
                                root.get("confirmedRequests")))
                        : cb.conjunction());
    }

    private static Specification<Event> stateEquals() {
        return ((root, query, cb) -> cb.equal(root.get("eventState"), EventState.PUBLISHED));
    }
}