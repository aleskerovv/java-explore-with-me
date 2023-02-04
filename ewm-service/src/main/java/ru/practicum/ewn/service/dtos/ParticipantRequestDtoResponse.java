package ru.practicum.ewn.service.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.ewn.service.enums.RequestStatus;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class ParticipantRequestDtoResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime created;
    Long event;
    Integer id;
    Long requester;
    RequestStatus requestStatus;
}
