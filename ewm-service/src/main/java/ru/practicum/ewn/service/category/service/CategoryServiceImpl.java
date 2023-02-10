package ru.practicum.ewn.service.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewn.service.category.dao.CategoryRepository;
import ru.practicum.ewn.service.category.dto.CategoryDto;
import ru.practicum.ewn.service.category.dto.CategoryDtoCreate;
import ru.practicum.ewn.service.category.mapper.CategoryMapper;
import ru.practicum.ewn.service.category.model.Category;
import ru.practicum.ewn.service.handlers.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CategoryDto createCategory(CategoryDtoCreate categoryDtoCreate) {
        log.info("creating new category");

        Category category = categoryRepository.save(categoryMapper.toEntity(categoryDtoCreate));

        log.info("created new category: {}", category);

        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CategoryDto updateCategory(CategoryDtoCreate categoryDtoCreate, Long categoryId) {
        log.info("updating category with id {}", categoryId);


        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(String.format("category with id %d not found", categoryId)));

        category.setName(categoryDtoCreate.getName());

        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("category with id %d not found", id)));

        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> findCategories(Integer from, Integer size) {
        Pageable pg = PageRequest.of(from, size);

        return categoryRepository.findAll(pg).stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
