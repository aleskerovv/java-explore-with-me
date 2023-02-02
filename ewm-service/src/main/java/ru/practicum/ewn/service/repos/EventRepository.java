package ru.practicum.ewn.service.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewn.service.model.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT ev FROM Event ev " +
            "JOIN FETCH ev.initiator evi " +
            "JOIN FETCH ev.location evl " +
            "JOIN FETCH ev.category evc")
    List<Event> findAllWithOptionals(Pageable pageable);

    List<Event> findAllByInitiatorId(Long id, Pageable pageable);
}