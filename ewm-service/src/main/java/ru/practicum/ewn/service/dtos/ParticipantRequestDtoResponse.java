package ru.practicum.ewn.service.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewn.service.enums.RequestStatus;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class ParticipantRequestDtoResponse {
    LocalDateTime created;
    Integer event;
    Integer id;
    Integer requester;
    RequestStatus requestStatus;
}
