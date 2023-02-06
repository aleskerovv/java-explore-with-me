package ru.practicum.ewn.service.compilations.model;

import lombok.*;
import lombok.experimental.Accessors;
import ru.practicum.ewn.service.events.model.Event;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    private Long id;
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Event> events;
    private Boolean pinned;
    private String title;
}
