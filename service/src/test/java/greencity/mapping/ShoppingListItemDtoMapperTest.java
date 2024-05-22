package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.shoppinglistitem.ShoppingListItemDto;
import greencity.entity.ShoppingListItem;
import greencity.entity.localization.ShoppingListItemTranslation;
import greencity.enums.ShoppingListItemStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingListItemDtoMapperTest {
    @InjectMocks
    ShoppingListItemDtoMapper mapper;
    @Mock
    ShoppingListItemTranslation shoppingListItemTranslation;

    @Test
    void convertTest() {
        ShoppingListItem expected = ModelUtils.getShoppingListItem();
        when(shoppingListItemTranslation.getShoppingListItem()).thenReturn(expected);
        when(shoppingListItemTranslation.getContent())
                .thenReturn(expected.getTranslations().getFirst().getContent());

        ShoppingListItemDto actual = mapper.convert(shoppingListItemTranslation);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTranslations().getFirst().getContent(), actual.getText());
        assertEquals(ShoppingListItemStatus.ACTIVE.toString(), actual.getStatus());

    }
}