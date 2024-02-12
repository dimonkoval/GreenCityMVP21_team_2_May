package greencity.dto.shoppinglistitem;

import lombok.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CustomShoppingListItemRequestDto {
    @NotNull
    @Min(1)
    private Long id;
}
