package greencity.dto.habit;

import greencity.constant.ServiceValidationConstants;
import greencity.dto.habittranslation.HabitTranslationDto;
import greencity.dto.shoppinglistitem.CustomShoppingListItemResponseDto;
import greencity.dto.shoppinglistitem.ShoppingListItemDto;
import greencity.enums.HabitAssignStatus;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class HabitDto {
    private Integer defaultDuration;
    private Long amountAcquiredUsers;
    private HabitTranslationDto habitTranslation;
    private Long id;
    private String image;
    @Min(value = 1, message = ServiceValidationConstants.HABIT_COMPLEXITY)
    @Max(value = 3, message = ServiceValidationConstants.HABIT_COMPLEXITY)
    private Integer complexity;
    private List<String> tags;
    private List<ShoppingListItemDto> shoppingListItems;
    private List<CustomShoppingListItemResponseDto> customShoppingListItems;
    private Boolean isCustomHabit;
    private Long usersIdWhoCreatedCustomHabit;
    private HabitAssignStatus habitAssignStatus;
}
