package ru.practicum.ewn.service.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewn.service.category.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}