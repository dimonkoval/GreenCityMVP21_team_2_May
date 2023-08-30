package greencity.dto.user;

import greencity.dto.shoppinglistitem.CustomShoppingListItemRequestDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserCustomShoppingListItemDto {
    private CustomShoppingListItemRequestDto customShoppingListItem;
}
