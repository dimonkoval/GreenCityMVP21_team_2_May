package greencity.dto.shoppinglistitem;

import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
public class CustomShoppingListItemSaveRequestDto {
    @NotBlank
    private String text;
}
