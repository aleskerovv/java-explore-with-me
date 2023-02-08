package ru.practicum.ewn.service.events.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewn.service.events.model.Event;
import ru.practicum.ewn.service.users.model.User;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByInitiatorId(Long id, Pageable pageable);

    List<Event> findAll(Specification<Event> specification, Pageable pageable);

    Event findEventByIdAndInitiator(Long id, User initiator);

    List<Event> findEventsByIdIn(List<Long> id);

}