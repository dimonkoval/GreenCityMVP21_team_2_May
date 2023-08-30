package greencity.dto.user;

import greencity.enums.ShoppingListItemStatus;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class UserShoppingListItemResponseDto {
    @Min(1L)
    private Long id;
    @NotEmpty
    private String text;
    @NotNull
    private ShoppingListItemStatus status;
}
