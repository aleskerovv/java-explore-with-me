package ru.practicum.ewn.service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewn.service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}