package greencity.dto.event;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventResponseDayInfoDto {
    private boolean isAllDay;

    private boolean isOnline;

    private LocalTime startTime;

    private LocalTime endTime;

    private int dayNumber;

    private LocalDate date;

    private String location;
}
