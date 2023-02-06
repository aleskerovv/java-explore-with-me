package ru.practicum.ewn.service.events.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Embeddable
public class Location {
    private Float lat;
    private Float lon;
}
