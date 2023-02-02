package ru.practicum.ewn.service.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class EventRequestStatusUpdateResult {
    List<ParticipantRequestDtoResponse> confirmedRequests;
    List<ParticipantRequestDtoResponse> rejectedRequests;
}
