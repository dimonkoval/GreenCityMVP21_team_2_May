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
public class EventDayInfo {

    private Long id;

    private boolean isAllDay;

    private boolean isEventOpen = true;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer dayCount;

    private LocalDate date;

    private EventLocation eventLocations;

//    @ManyToOne
    private EventModelDto event;
}
