package greencity.dto.shoppinglistitem;

import greencity.enums.ShoppingListItemStatus;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
