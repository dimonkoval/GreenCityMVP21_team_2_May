package greencity.dto.user;

import greencity.dto.habit.HabitAssignVO;
import greencity.dto.shoppinglistitem.ShoppingListItemVO;
import greencity.enums.ShoppingListItemStatus;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class UserShoppingListItemVO {
    private Long id;

    private HabitAssignVO habitAssign;

    private ShoppingListItemVO shoppingListItemVO;

    private ShoppingListItemStatus status = ShoppingListItemStatus.ACTIVE;

    private LocalDateTime dateCompleted;
}
