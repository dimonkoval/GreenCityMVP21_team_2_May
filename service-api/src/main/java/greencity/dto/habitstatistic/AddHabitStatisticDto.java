package greencity.dto.habitstatistic;

import greencity.constant.ServiceValidationConstants;
import greencity.enums.HabitRate;
import lombok.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class AddHabitStatisticDto {
    @Min(ServiceValidationConstants.MIN_AMOUNT_OF_ITEMS)
    @Max(ServiceValidationConstants.MAX_AMOUNT_OF_ITEMS)
    @NotNull
    private Integer amountOfItems;
    @NotNull
    private HabitRate habitRate;
    @NotNull
    private ZonedDateTime createDate;
}
