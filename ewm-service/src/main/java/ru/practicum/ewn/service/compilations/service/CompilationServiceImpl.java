package ru.practicum.ewn.service.compilations.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewn.service.compilations.dao.CompilationRepository;
import ru.practicum.ewn.service.compilations.dto.CompilationDto;
import ru.practicum.ewn.service.compilations.dto.CompilationDtoCreate;
import ru.practicum.ewn.service.compilations.dto.CompilationUpdateDto;
import ru.practicum.ewn.service.compilations.mapper.CompilationMapper;
import ru.practicum.ewn.service.compilations.model.Compilation;
import ru.practicum.ewn.service.events.dao.EventRepository;
import ru.practicum.ewn.service.events.model.Event;
import ru.practicum.ewn.service.handlers.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CompilationDto createCompilation(CompilationDtoCreate compilationDto) {
        log.info("creating new compilation {}", compilationDto);

        List<Event> events = eventRepository.findEventsByIdIn(compilationDto.getEvents());

        Compilation compilation = compilationMapper.toEntity(compilationDto, events);

        compilationRepository.save(compilation);

        return compilationMapper.toDto(compilation);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CompilationDto updateCompilation(Long compilationId, CompilationUpdateDto compilationDto) {
        log.info("updating compilation with id {}", compilationId);

        Compilation compilation = getCompilationIfExists(compilationId);

        List<Event> events = eventRepository.findEventsByIdIn(compilationDto.getEvents());

        compilationMapper.partialUpdate(compilationDto, compilation, events);

        return compilationMapper.toDto(compilation);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteCompilation(Long compilationId) {
        log.info("deleting compilation with id {}", compilationId);
        compilationRepository.deleteById(compilationId);
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilationById(Long compilationId) {
        Compilation compilation = compilationRepository.findById(compilationId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Compilation with id %d not found", compilationId)));

        return compilationMapper.toDto(compilation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> findAllCompilations(Boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);

        return compilationRepository.findCompilationByPinned(pinned, pageable).stream()
                .map(compilationMapper::toDto)
                .collect(Collectors.toList());
    }

    private Compilation getCompilationIfExists(Long compilationId) {
        return compilationRepository.findById(compilationId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Compilation with id %d not found", compilationId)));
    }
}