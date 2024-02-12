package greencity.dto.shoppinglistitem;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
public class ShoppingListItemRequestDto {
    @NotNull
    @Min(1)
    private Long id = 1L;
}
