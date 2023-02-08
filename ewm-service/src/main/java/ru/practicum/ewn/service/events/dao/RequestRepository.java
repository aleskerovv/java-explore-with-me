package ru.practicum.ewn.service.events.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewn.service.events.model.Request;
import ru.practicum.ewn.service.events.model.RequestCount;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT COUNT(r) " +
            "FROM Request r " +
            "WHERE r.event.id = :eventId")
    RequestsCount findRequestsCount(Long eventId);

    List<Request> findRequestByRequesterIdAndEventId(Long requesterId, Long eventId);

    List<Request> findRequestByRequesterId(Long requesterId);

    Optional<Request> findRequestByIdAndRequesterId(Long requestId, Long requesterId);

    @Query("SELECT r from Request r " +
            "where r.event.id = :eventId " +
            "and r.event.initiator.id = :initiatorId")
    List<Request> findRequestsByInitiatorIdIdAndEventId(Long initiatorId, Long eventId);

    @Query("SELECT r from Request r " +
            "where r.event.id = :eventId " +
            "and r.event.initiator.id = :initiatorId " +
            "and r.id in (:requestsId)")
    List<Request> findRequestsByInitiatorIdIdAndEventId(Long initiatorId, Long eventId, List<Long> requestsId);

    @Query("SELECT count(r) from Request r " +
            "where r.status = 'CONFIRMED' " +
            "and r.event.id = :eventId")
    Integer getConfirmedRequestsCount(Long eventId);

    @Query("SELECT r.event.id as eventId, count(r.event.id) as requests FROM Request r " +
            "where r.status = 'CONFIRMED' " +
            "and r.event.id in (:eventIds) " +
            "group by eventId")
    List<RequestCount> getRequestsForEvents(Set<Long> eventIds);
}