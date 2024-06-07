package greencity.dto.event;

import greencity.dto.event.model.EventStatus;
import lombok.*;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventSaveDayInfoDto {
    private boolean isAllDay;

    private ZonedDateTime startDateTime;

    private ZonedDateTime endDateTime;

    private int dayNumber;

    private EventStatus status;

    private String link;

    private EventAddressDto address;
}
