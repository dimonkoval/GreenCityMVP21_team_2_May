package greencity.dto.event;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventSaveDayInfoDto {
    private boolean isAllDay;

    private boolean isOnline;

    private ZonedDateTime startDateTime;

    private ZonedDateTime endDateTime;

    private int dayNumber;

    private String location;
}
