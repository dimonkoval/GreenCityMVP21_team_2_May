package greencity.dto.shoppinglistitem;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
