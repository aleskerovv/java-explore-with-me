package ru.practicum.ewn.service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewn.service.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}