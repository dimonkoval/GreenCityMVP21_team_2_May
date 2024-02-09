package greencity.dto.shoppinglistitem;

import greencity.enums.ShoppingListItemStatus;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class CustomShoppingListItemResponseDto {
    @Min(1)
    private Long id;
    @NotEmpty
    private String text;
    @NotNull
    private ShoppingListItemStatus status;
}
