package ru.practicum.ewm.stat.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.stat.service.dto.HitStats;
import ru.practicum.ewm.stat.service.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query(value = "select app, uri, count(ip) as hits " +
            "from statistics " +
            "where hit_date between :startDate and :endDate " +
            "and uri in (:uri)" +
            "group by app, uri " +
            "order by hits desc", nativeQuery = true)
    List<HitStats> getHits(LocalDateTime startDate, LocalDateTime endDate, Collection<String> uri);

    @Query(value = "select app, uri, count(distinct ip) as hits " +
            "from statistics " +
            "where hit_date between :startDate and :endDate " +
            "and uri in (:uri)" +
            "group by app, uri " +
            "order by hits desc", nativeQuery = true)
    List<HitStats> getUniqueHits(LocalDateTime startDate, LocalDateTime endDate, Collection<String> uri);
}
