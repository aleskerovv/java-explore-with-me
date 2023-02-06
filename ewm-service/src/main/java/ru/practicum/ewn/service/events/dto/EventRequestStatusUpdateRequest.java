package ru.practicum.ewn.service.events.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewn.service.enums.RequestStatus;

import java.util.List;

@Value
@Builder
@Jacksonized
public class EventRequestStatusUpdateRequest {
    List<Long> requestIds;
    RequestStatus status;
}
