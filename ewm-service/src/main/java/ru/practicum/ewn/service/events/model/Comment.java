package ru.practicum.ewn.service.events.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.ewn.service.enums.CommentState;
import ru.practicum.ewn.service.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    @ToString.Exclude
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @ToString.Exclude
    private User author;
    @CreationTimestamp
    private LocalDateTime created;
    @Enumerated(value = EnumType.STRING)
    private CommentState commentState;
    private LocalDateTime published;
}
