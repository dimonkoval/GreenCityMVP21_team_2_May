package greencity.dto.event;

import greencity.annotations.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @ValidAddress(message = "Please add address to the event.")
    @ValidLink(message = "Please add a link to the event. The link must start with http(s)://")
    @NotEmptyEventDateTime(message = "Please, enter at least one dateTime for Event")
    @EventDateAfterOneHour(message = "First Event should be at least one hour after now")
    @ValidSequenceEventDates(message = "Each event date must follow the previous one")
    @StartBeforeEndTime(message = "End time cannot be before Start time")
    @ValidAllDayEvent(message = "An all-day event should begin at 00:00 and conclude at 23:59")
    @ValidSameDay(message = "StartDate and EndDate must be the same day")
    @UniqueEventDates
    @Size(max = 7, min = 1)
    private List<EventSaveDayInfoDto> daysInfo;

    @NotBlank
    @Size(min = 20, max = 63206)
    private String description;

    private boolean isOpen = true;

    private int mainImageNumber = 0;

    private List<String> tags;
}