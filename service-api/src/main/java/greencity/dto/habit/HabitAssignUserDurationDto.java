package greencity.dto.habit;

import greencity.enums.HabitAssignStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class HabitAssignUserDurationDto {
    private Long habitAssignId;
    private Long userId;
    private Long habitId;
    private HabitAssignStatus status;
    private Integer workingDays;
    private Integer duration;
}
