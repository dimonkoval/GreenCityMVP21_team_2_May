package greencity.repository;

import greencity.entity.EventComment;
import greencity.entity.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCommentRepo extends JpaRepository<EventComment, Long> {
    /**
     * The method returns the count of not deleted comments, specified by eventId.
     *
     * @return count of comments, specified by {@link Event}.
     */
    int countByEvent(Event event);

    Page<EventComment> findAllByEvent(Event event);
}
