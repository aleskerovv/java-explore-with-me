package ru.practicum.ewn.service.model;

import lombok.*;
import ru.practicum.ewn.service.enums.EventState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String annotation;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ToString.Exclude
    private Category category;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime eventDate;
    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    @ToString.Exclude
    private User initiator;
    private LocalDateTime publishedOn;
    @Enumerated(EnumType.STRING)
    private EventState eventState;
}
