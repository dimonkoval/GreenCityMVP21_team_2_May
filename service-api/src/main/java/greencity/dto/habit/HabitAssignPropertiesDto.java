package greencity.dto.habit;

import greencity.constant.AppConstant;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class HabitAssignPropertiesDto {
    @NotNull
    @Min(AppConstant.MIN_DAYS_DURATION)
    @Max(AppConstant.MAX_DAYS_DURATION)
    private Integer duration;

    private List<Long> defaultShoppingListItems;
}
