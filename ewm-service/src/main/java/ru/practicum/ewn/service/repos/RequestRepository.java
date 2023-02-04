package ru.practicum.ewn.service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewn.service.model.Request;
import ru.practicum.ewn.service.utils.RequestsCount;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("SELECT COUNT(r) " +
            "FROM Request r " +
            "WHERE r.event.id = :eventId")
    RequestsCount findRequestsCount(Long eventId);

    List<Request> findRequestByRequesterId(Long requesterId);
}