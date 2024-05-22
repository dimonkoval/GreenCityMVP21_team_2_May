package greencity.mapping;

import greencity.dto.shoppinglistitem.ShoppingListItemRequestDto;
import greencity.entity.UserShoppingListItem;
import greencity.enums.ShoppingListItemStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ShoppingListItemRequestDtoMapperTest {
    @InjectMocks
    ShoppingListItemRequestDtoMapper mapper;

    @Test
    void convertTest() {
        ShoppingListItemRequestDto expected = new ShoppingListItemRequestDto();
        expected.setId(1L);

        UserShoppingListItem result = mapper.convert(expected);

        assertEquals(expected.getId(), result.getShoppingListItem().getId());
        assertEquals(ShoppingListItemStatus.ACTIVE, result.getStatus());
    }
}