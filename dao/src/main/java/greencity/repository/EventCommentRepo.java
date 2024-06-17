package greencity.repository;

import greencity.entity.EcoNewsComment;
import greencity.entity.EventComment;
import greencity.entity.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCommentRepo extends JpaRepository<EventComment, Long> {
    /**
     * The method returns the count of not deleted comments, specified by eventId.
     *
     * @return count of comments, specified by {@link Event}.
     */
    int countByEvent(Event event);

    /**
     * Method returns all {@link EventComment} by page.
     *
     * @param pageable page of news.
     * @param event of {@link Event} for which comments we
     *                  search.
     * @return all active {@link EventComment} by page order by created date.
     */
    Page<EventComment> findAllByEventOrderByCreatedDateDesc(Event event, Pageable pageable);
}
