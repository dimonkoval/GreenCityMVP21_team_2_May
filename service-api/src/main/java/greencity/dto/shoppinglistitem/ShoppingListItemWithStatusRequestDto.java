package greencity.dto.shoppinglistitem;

import greencity.enums.ShoppingListItemStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ShoppingListItemWithStatusRequestDto extends ShoppingListItemRequestDto {
    @NotNull
    private ShoppingListItemStatus status;
}
