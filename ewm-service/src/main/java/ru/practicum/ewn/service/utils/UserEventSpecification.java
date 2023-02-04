package ru.practicum.ewn.service.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewn.service.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@UtilityClass
public class UserEventSpecification {
    public static Specification<Event> filtersFromUser(UserEventFilter eventFilter) {
        return annotationFilter(eventFilter.getText())
                .and(descriptionFilter(eventFilter.getText()))
                .and(dateFilter(eventFilter.getRangeStart(), eventFilter.getRangeEnd()))
//                .and(availableFilter(eventFilter.getOnlyAvailable()))
//                .and(sortFilter(eventFilter.getSort()))
                .and(categoriesFilter(eventFilter.getCategories()))
                .and(paidFilter(eventFilter.getPaid()));

    }

    private static Specification<Event> annotationFilter(final String text) {
        return (root, query, criteriaBuilder) ->
                text == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(root.get("annotation".toLowerCase()),
                        "%" + text.toLowerCase() + "%");
    }

    private static Specification<Event> descriptionFilter(final String text) {
        return (root, query, criteriaBuilder) ->
                text == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(root.get("description".toLowerCase()),
                        "%" + text.toLowerCase() + "%");
    }

    private static Specification<Event> dateFilter(final LocalDateTime start,
                                                   final LocalDateTime end) {
        return ((root, query, criteriaBuilder) ->
                start == null && end == null
                        ? criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), LocalDateTime.now())
                        : criteriaBuilder.between(root.get("eventDate"), start, end));
    }

    private static Specification<Event> availableFilter(final Boolean available) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("onlyAvailable"), available));
    }

    private static Specification<Event> paidFilter(final Boolean paid) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("paid"), paid));
    }

    private static Specification<Event> categoriesFilter(final List<Long> categoriesIds) {
        return ((root, query, criteriaBuilder) ->
                categoriesIds == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.in(root.get("category").get("id")).value(categoriesIds));
    }
}