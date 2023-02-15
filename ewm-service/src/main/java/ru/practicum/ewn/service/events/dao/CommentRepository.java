package ru.practicum.ewn.service.events.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewn.service.events.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c from Comment c " +
            "WHERE c.event.id = :eventId " +
            "and c.commentState = 'PUBLISHED'")
    List<Comment> findCommentsByEventId(Long eventId, Pageable pageable);

    @Query("SELECT c from Comment c " +
            "WHERE c.author.id = :authorId " +
            "and c.commentState = 'PUBLISHED'")
    List<Comment> findCommentsByAuthorId(Long authorId, Pageable pageable);

    @Query("SELECT c from Comment c " +
            "WHERE c.author.id = :authorId " +
            "and c.id = :commentId")
    Comment findCommentByAuthorIdAndId(Long authorId, Long commentId);
}
