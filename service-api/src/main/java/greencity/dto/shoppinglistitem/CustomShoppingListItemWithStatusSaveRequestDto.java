package greencity.dto.shoppinglistitem;

import greencity.enums.ShoppingListItemStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class CustomShoppingListItemWithStatusSaveRequestDto extends CustomShoppingListItemSaveRequestDto {
    ShoppingListItemStatus status;
}
