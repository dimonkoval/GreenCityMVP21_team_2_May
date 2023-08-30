package greencity.dto.shoppinglistitem;

import greencity.dto.language.LanguageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingListItemTranslationDTO {
    private Long id;

    private LanguageVO language;

    private String content;
}
