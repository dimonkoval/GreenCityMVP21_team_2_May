package greencity.repository;

import greencity.entity.event.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

//@Repository
@Component //need to be removed
public class EventRepo {

    private List<Event> events = new ArrayList<>();
    private AtomicLong eventId = new AtomicLong();

    public Event save(Event event) {
        event.setId(eventId.incrementAndGet());
        events.add(event);
        return event;
    }

    public List<Event> findAll(Pageable pageable) {
        return events;
    }

    public Optional<Event> findById(Long id) {
        return Optional.ofNullable(events.stream().filter(e -> e.getId().equals(id)).toList().get(0));
    }

    public List<Event> findAllByAuthorId(Pageable pageable, Long userId) {
        return events.stream().filter(e -> e.getAuthor().getId().equals(userId)).toList();
    }
}
