package ru.practicum.ewn.service.model;

import lombok.*;
import lombok.experimental.Accessors;
import ru.practicum.ewn.service.enums.EventState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String annotation;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private Category category;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime eventDate;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "initiator_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private User initiator;
    private LocalDateTime publishedOn;
    @Enumerated(EnumType.STRING)
    private EventState eventState;
}
