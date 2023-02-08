package ru.practicum.ewn.service.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewn.service.compilations.dto.CompilationDto;
import ru.practicum.ewn.service.compilations.service.CompilationService;

import java.util.List;

@RestController
@RequestMapping("compilations")
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto findById(@PathVariable Long id) {
        return compilationService.getCompilationById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getCompilations(@RequestParam(required = false, defaultValue = "false") Boolean pinned,
                                                @RequestParam(name = "from", defaultValue = "0", required = false) Integer from,
                                                @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        return compilationService.findAllCompilations(pinned, from, size);
    }


}
