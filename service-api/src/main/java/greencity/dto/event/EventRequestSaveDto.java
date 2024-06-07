package greencity.dto.event;

import greencity.annotations.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    @Size(max = 70)
    private String title;

    @NotNull
    @ValidEventDateTime
    //@ValidLocation
    @NotEmptyEventDateTime
    @EventDateAfterOneHour
    @ValidSequenceEventDates
    @StartBeforeEndTime
    @ValidAllDayEvent
    @Size(max = 7, min = 1)
    private List<EventSaveDayInfoDto> daysInfo;

    @NotBlank
    @Size(min = 20, max = 63206)
    private String description;

    private boolean isOpen = true;

    private int mainImageNumber = 0;

    private List<String> tags;
}
