package ru.practicum.ewn.service.compilations.service;

import ru.practicum.ewn.service.compilations.dto.CompilationDto;
import ru.practicum.ewn.service.compilations.dto.CompilationDtoCreate;
import ru.practicum.ewn.service.compilations.dto.CompilationUpdateDto;

import java.util.List;

public interface CompilationService {
    CompilationDto createCompilation(CompilationDtoCreate compilationDto);

    CompilationDto updateCompilation(Long compilationId, CompilationUpdateDto compilationDto);

    void deleteCompilation(Long compilationId);

    CompilationDto getCompilationById(Long compilationId);

    List<CompilationDto> findAllCompilations(Boolean pinned, int from, int size);
}
