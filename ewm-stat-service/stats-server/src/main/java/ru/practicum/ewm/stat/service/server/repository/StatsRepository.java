package ru.practicum.ewm.stat.service.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.dto.StatisticCount;
import ru.practicum.ewm.stat.service.server.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query(value = "SELECT eh.app.name as app, eh.uri as uri, COUNT(eh.ip) as hits " +
            "FROM EndpointHit eh " +
            "WHERE eh.hitDate between :startDate and :endDate " +
            "AND eh.uri in (:uri) " +
            "GROUP BY eh.app.name, eh.uri " +
            "ORDER BY hits desc")
    List<StatisticCount> getHits(LocalDateTime startDate, LocalDateTime endDate, Collection<String> uri);

    @Query(value = "SELECT eh.app.name as app, eh.uri as uri, COUNT(DISTINCT eh.ip) as hits " +
            "FROM EndpointHit eh " +
            "WHERE eh.hitDate between :startDate and :endDate " +
            "AND eh.uri in (:uri) " +
            "GROUP BY eh.app.name, eh.uri " +
            "ORDER BY hits desc")
    List<StatisticCount> getUniqueHits(LocalDateTime startDate, LocalDateTime endDate, Collection<String> uri);

    @Query(value = "SELECT eh.app.name as app, eh.uri as uri, COUNT(DISTINCT eh.ip) as hits " +
            "FROM EndpointHit eh " +
            "WHERE eh.hitDate between :startDate and :endDate " +
            "GROUP BY eh.app.name, eh.uri " +
            "ORDER BY hits desc")
    List<StatisticCount> getAllUniqueHits(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT eh.app.name as app, eh.uri as uri, COUNT(eh.ip) as hits " +
            "FROM EndpointHit eh " +
            "WHERE eh.hitDate between :startDate and :endDate " +
            "GROUP BY eh.app.name, eh.uri " +
            "ORDER BY hits desc")
    List<StatisticCount> getAllHits(LocalDateTime startDate, LocalDateTime endDate);
}