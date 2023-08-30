package greencity.dto.habitstatistic;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class CalendarUsefulHabitsDto {
    private ZonedDateTime creationDate;
    private List<HabitLogItemDto> allItemsPerMonth;
    private List<HabitLogItemDto> differenceUnTakenItemsWithPreviousDay;
}
