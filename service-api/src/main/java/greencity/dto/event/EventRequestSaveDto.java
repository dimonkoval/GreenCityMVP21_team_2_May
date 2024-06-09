package greencity.dto.event;

import greencity.annotations.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventRequestSaveDto {
    @NotBlank(message = "Title cannot be blank")
    @Size(message = "Title must be a maximum of 70 characters", max = 70)
    private String title;

    @NotEmptyEventDateTime(message = "Please, enter at least one dateTime for Event")
    @EventDateAfterOneHour(message = "First Event should be at least one hour after now")
    @ValidSequenceEventDates(message = "Each event date must follow the previous one", priority = 0)
    @StartBeforeEndTime(message = "End time cannot be before Start time")
    @ValidAllDayEvent(message = "An all-day event should begin at 00:00 and conclude at 23:59")
    @UniqueEventDates(message = "You cannot use the same date for multiple days", priority = 1)
    @Size(max = 7, min = 1)
    private List<EventSaveDayInfoDto> daysInfo;

    @NotBlank
    @Size(min = 20, max = 63206)
    private String description;

    private boolean isOpen = true;

    private int mainImageNumber = 0;

    private List<String> tags;
}
