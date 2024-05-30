package greencity.dto.event;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventTime {

    private Long id;

    private boolean allDay;


    private LocalTime startTime;

    private LocalTime endTime;

    private Integer dayCount;

//    @ManyToOne
    private EventModelDto event;
}
