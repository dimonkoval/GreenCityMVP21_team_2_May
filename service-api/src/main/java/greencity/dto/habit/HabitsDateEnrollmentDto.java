package greencity.dto.habit;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class HabitsDateEnrollmentDto {
    private LocalDate enrollDate;
    private List<HabitEnrollDto> habitAssigns;
}
