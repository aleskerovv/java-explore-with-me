package ru.practicum.ewn.service.dtos;

import java.util.List;

public class EventRequestStatusUpdateResult {
    List<ParticipantRequestDtoResponse> confirmedRequests;
    List<ParticipantRequestDtoResponse> rejectedRequests;
}
