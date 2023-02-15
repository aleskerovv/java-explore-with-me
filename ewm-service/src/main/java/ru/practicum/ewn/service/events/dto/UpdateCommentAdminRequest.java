package ru.practicum.ewn.service.events.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewn.service.enums.CommentStateAction;

@Getter
@Setter
@Builder
@Jacksonized
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdateCommentAdminRequest {
    private CommentStateAction commentState;
}
