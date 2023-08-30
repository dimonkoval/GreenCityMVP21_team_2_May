package greencity.dto.shoppinglistitem;

import lombok.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BulkCustomShoppingListItemDto {
    @Valid
    List<@Valid CustomShoppingListItemResponseDto> customShoppingListItemDto;
}
