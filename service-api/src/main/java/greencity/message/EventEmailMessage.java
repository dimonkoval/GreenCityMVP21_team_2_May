package greencity.message;

import greencity.dto.event.EventAddressDto;
import greencity.enums.EventStatus;
import lombok.*;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private EventAddressDto address;
    private String linkToEvent;
}
