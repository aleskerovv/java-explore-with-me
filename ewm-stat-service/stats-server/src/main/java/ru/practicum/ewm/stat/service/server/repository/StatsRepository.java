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

    @Query(value = "select app, uri, count(ip) as hits " +
            "from statistics " +
            "where hit_date between :startDate and :endDate and uri in :uri " +
            "group by app, uri " +
            "order by hits desc", nativeQuery = true)
    List<StatisticCount> getHits(LocalDateTime startDate, LocalDateTime endDate, Collection<String> uri);

    @Query(value = "select app, uri, count(distinct ip) as hits " +
            "from statistics " +
            "where hit_date between :startDate and :endDate and uri in :uri " +
            "group by app, uri " +
            "order by hits desc", nativeQuery = true)
    List<StatisticCount> getUniqueHits(LocalDateTime startDate, LocalDateTime endDate, Collection<String> uri);

    @Query(value = "select app, uri, count(distinct ip) as hits " +
            "from statistics " +
            "where hit_date between :startDate and :endDate " +
            "group by app, uri " +
            "order by hits desc", nativeQuery = true)
    List<StatisticCount> getAllUniqueHits(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select app, uri, count(ip) as hits " +
            "from statistics " +
            "where hit_date between :startDate and :endDate " +
            "group by app, uri " +
            "order by hits desc", nativeQuery = true)
    List<StatisticCount> getAllHits(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT h.app as app, h.uri as uri, count(h.ip) as hits " +
            "from EndpointHit h " +
            "where uri = :uri " +
            "group by app, uri")
    StatisticCount getByUri(String uri);
}