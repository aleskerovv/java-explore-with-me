package ru.practicum.ewn.service.events.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestCountStub {
    private Long eventId;
    private Long requests;
}
