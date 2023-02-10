package ru.practicum.ewn.service.events.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Jacksonized
public class EventRequestStatusUpdateResult {
    private List<ParticipantRequestDtoResponse> confirmedRequests;
    private List<ParticipantRequestDtoResponse> rejectedRequests;
}
