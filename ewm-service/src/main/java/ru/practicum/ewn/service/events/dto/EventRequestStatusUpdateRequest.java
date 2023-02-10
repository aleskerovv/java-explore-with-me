package ru.practicum.ewn.service.events.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewn.service.enums.RequestStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Jacksonized
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private RequestStatus status;
}
