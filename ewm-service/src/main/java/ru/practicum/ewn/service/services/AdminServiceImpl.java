package ru.practicum.ewn.service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewn.service.repos.CategoryRepository;
import ru.practicum.ewn.service.repos.CompilationRepository;
import ru.practicum.ewn.service.repos.EventRepository;
import ru.practicum.ewn.service.repos.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl {
    private final CategoryRepository categoryRepository;
    private final CompilationRepository compilationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
}
