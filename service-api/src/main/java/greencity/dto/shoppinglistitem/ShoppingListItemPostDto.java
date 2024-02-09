package greencity.dto.shoppinglistitem;

import greencity.annotations.LanguageTranslationConstraint;
import greencity.dto.language.LanguageTranslationDTO;
import lombok.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ShoppingListItemPostDto {
    @Valid
    @LanguageTranslationConstraint
    private List<LanguageTranslationDTO> translations;

    @Valid
    @NotNull
    private ShoppingListItemRequestDto shoppingListItem;
}
