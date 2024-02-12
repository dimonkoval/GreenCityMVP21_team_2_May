package greencity.dto.habitstatuscalendar;

import greencity.dto.habit.HabitAssignVO;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class HabitStatusCalendarVO {
    private Long id;
    private LocalDate enrollDate;
    private HabitAssignVO habitAssignVO;
}
