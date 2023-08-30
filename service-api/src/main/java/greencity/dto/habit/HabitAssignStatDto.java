package greencity.dto.habit;

import greencity.enums.HabitAssignStatus;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class HabitAssignStatDto {
    @NotNull
    private HabitAssignStatus status;
}
