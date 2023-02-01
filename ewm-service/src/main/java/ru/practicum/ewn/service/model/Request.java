package ru.practicum.ewn.service.model;

import lombok.*;
import lombok.experimental.Accessors;
import ru.practicum.ewn.service.enums.RequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ToString.Exclude
    private Event event;
    @OneToOne
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    private User requester;
    RequestStatus status;
}
