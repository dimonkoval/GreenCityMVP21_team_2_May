package greencity.message;

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
    private String message;
    private String description;
    private boolean isOpen;
    private boolean isOnline;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private String location;
}
