package greencity.dto.event.model;

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
public class EventDayInfo {

    private Long id;

    private boolean isAllDay;

    private ZonedDateTime startDateTime;

    private ZonedDateTime endDateTime;

    private int dayNumber;

    private String link;

//@Embedded
    private EventAddress address;

//    @ManyToOne
    private EventModelDto event;
}
