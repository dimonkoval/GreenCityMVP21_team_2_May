package greencity.message;

import greencity.dto.event.model.EventAddress;
import greencity.dto.event.model.EventStatus;
import lombok.*;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class EventEmailMessage {
    private String email;
    private String subject;
    private String author;
    private String eventTitle;
    private String description;
    private boolean isOpen;
    private EventStatus status;
    private String link;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private EventAddress address;
}
