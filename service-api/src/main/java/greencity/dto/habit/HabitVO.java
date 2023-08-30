package greencity.dto.habit;

import greencity.constant.ServiceValidationConstants;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HabitVO {
    private Long id;
    private String image;
    @Min(value = 1, message = ServiceValidationConstants.HABIT_COMPLEXITY)
    @Max(value = 3, message = ServiceValidationConstants.HABIT_COMPLEXITY)
    private Integer complexity;
}
