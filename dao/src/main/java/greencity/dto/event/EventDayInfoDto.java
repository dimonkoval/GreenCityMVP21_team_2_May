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
public class EventDayInfoDto {
    private Long id;

    private boolean isAllDay;

    private boolean isOnline;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer dayCount;

    private LocalDate date;

    private EventLocationDto eventLocations;
}
