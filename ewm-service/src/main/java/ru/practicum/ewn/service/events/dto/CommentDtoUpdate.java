package ru.practicum.ewn.service.events.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@Jacksonized
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentDtoUpdate {
    @NotNull(message = "'text' can not be null")
    private String text;
}
