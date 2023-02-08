package ru.practicum.ewn.service.events.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import ru.practicum.ewn.service.category.model.Category;
import ru.practicum.ewn.service.compilations.model.Compilation;
import ru.practicum.ewn.service.enums.EventState;
import ru.practicum.ewn.service.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private Integer confirmedRequests;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime eventDate;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "lat", column = @Column(name = "latitude")),
    @AttributeOverride(name = "lon", column = @Column(name = "longitude"))})
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
    @ManyToMany(mappedBy = "events")
    @ToString.Exclude
    private Set<Compilation> compilations = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
