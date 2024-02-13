package greencity.dto.user;

import greencity.dto.shoppinglistitem.ShoppingListItemRequestDto;
import lombok.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserShoppingListItemDto {
    @Valid
    @NotNull
    private ShoppingListItemRequestDto shoppingListItemRequestDto;
}
