package ru.practicum.ewn.service.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewn.service.enums.EventState;
import ru.practicum.ewn.service.events.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@UtilityClass
public class AdminEventSpecification {
    public static Specification<Event> getAdminsFilters(AdminEventFilter filter) {
        return statesFilter(filter.getStates())
                .and(usersFilter(filter.getUsers()))
                .and(categoriesFilter(filter.getCategories()))
                .and(startDateFilter(filter.getRangeStart()))
                .and(endDateFilter(filter.getRangeEnd()));
    }

    private static Specification<Event> usersFilter(final List<Long> userIds) {
        return ((root, query, criteriaBuilder) ->
                userIds == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.in(root.get("initiator").get("id")).value(userIds));
    }

    private static Specification<Event> statesFilter(final List<EventState> states) {
        return ((root, query, criteriaBuilder) ->
                states == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.in(root.get("eventState")).value(states));
    }

    private static Specification<Event> categoriesFilter(final List<Long> categoriesIds) {
        return ((root, query, criteriaBuilder) ->
                categoriesIds == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.in(root.get("category").get("id")).value(categoriesIds));
    }

    private static Specification<Event> startDateFilter(final LocalDateTime startTime) {
        return ((root, query, criteriaBuilder) ->
                startTime == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), startTime));
    }

    private static Specification<Event> endDateFilter(final LocalDateTime endTime) {
        return ((root, query, criteriaBuilder) ->
                endTime == null ? criteriaBuilder.conjunction()
                        : criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), endTime));
    }
}
