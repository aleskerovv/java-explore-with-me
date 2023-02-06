package ru.practicum.ewn.service.users.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewn.service.users.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u " +
            "where u.id in (:ids)")
    List<User> findByParams(List<Long> ids, Pageable pageable);

    @Query("SELECT u FROM User u ")
    List<User> findByParams(Pageable pageable);

}