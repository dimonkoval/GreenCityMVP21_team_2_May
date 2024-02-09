package greencity.dto.shoppinglistitem;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ShoppingListItemManagementDto {
    @NotNull
    private Long id;

    private List<ShoppingListItemTranslationVO> translations;
}
