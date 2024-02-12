package greencity.dto.shoppinglistitem;

import lombok.*;
import jakarta.validation.Valid;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BulkDeleteCustomShoppingListItemDto {
    @Valid
    List<@Valid CustomShoppingListItemRequestDto> customShoppingListItems;
}
