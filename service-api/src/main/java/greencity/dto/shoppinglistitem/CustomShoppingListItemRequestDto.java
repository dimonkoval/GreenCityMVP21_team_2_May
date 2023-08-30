package greencity.dto.shoppinglistitem;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
