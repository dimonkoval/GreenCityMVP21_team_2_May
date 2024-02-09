package greencity.dto.user;

import lombok.*;

import jakarta.validation.Valid;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BulkSaveUserShoppingListItemDto {
    @Valid
    List<@Valid UserShoppingListItemDto> userShoppingListItems;
}
