package ru.practicum.ewn.service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewn.service.dtos.CategoryDto;
import ru.practicum.ewn.service.dtos.CategoryDtoCreate;
import ru.practicum.ewn.service.handlers.NotFoundException;
import ru.practicum.ewn.service.model.Category;
import ru.practicum.ewn.service.repos.CategoryRepository;
import ru.practicum.ewn.service.utils.CategoryMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto createCategory(CategoryDtoCreate categoryDtoCreate) {
        log.info("creating new category");

        Category category = categoryRepository.save(categoryMapper.toEntity(categoryDtoCreate));

        log.info("created new category: {}", category);

        return categoryMapper.toDto(category);
    }

    public CategoryDto updateCategory(CategoryDtoCreate categoryDtoCreate, Long categoryId) {
        log.info("updating category with id {}", categoryId);

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            Category categoryEntity = category.get();
            categoryEntity.setName(categoryDtoCreate.getName());
            return categoryMapper.toDto(categoryRepository.save(categoryEntity));
        } else {
            throw new NotFoundException(String.format("category with id %d not found", categoryId));
        }
    }

    //TODO: process 404 response if getBySameId after deleting
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("category with id %d not found", id)));

        return categoryMapper.toDto(category);
    }

    public List<CategoryDto> findCategories(Integer from, Integer size) {
        Pageable pg = PageRequest.of(from, size);

        return categoryRepository.findByParams(pg).stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }
}
