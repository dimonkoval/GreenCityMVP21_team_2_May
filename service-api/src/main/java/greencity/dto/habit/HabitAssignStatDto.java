package greencity.dto.habit;

import greencity.enums.HabitAssignStatus;
import lombok.*;

import jakarta.validation.constraints.NotNull;

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
