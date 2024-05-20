package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.shoppinglistitem.CustomShoppingListItemResponseDto;
import greencity.entity.CustomShoppingListItem;
import greencity.enums.ShoppingListItemStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomShoppingListResponseDtoMapperTest {
    @InjectMocks
    CustomShoppingListResponseDtoMapper mapper;

    @Test
    void convert() {
        CustomShoppingListItemResponseDto expected = ModelUtils.getCustomShoppingListItemResponseDto();
        CustomShoppingListItem itemToBeConverted = CustomShoppingListItem.builder()
                .id(expected.getId())
                .status(expected.getStatus())
                .text(expected.getText())
                .build();

        assertEquals(expected, mapper.convert(itemToBeConverted));
    }

    @Test
    void mapAllToList() {
        CustomShoppingListItemResponseDto expected1 = ModelUtils.getCustomShoppingListItemResponseDto();
        CustomShoppingListItem itemToBeConverted1 = CustomShoppingListItem.builder()
                .id(expected1.getId())
                .status(expected1.getStatus())
                .text(expected1.getText())
                .build();
        CustomShoppingListItemResponseDto expected2 = CustomShoppingListItemResponseDto.builder()
                .id(2L)
                .status(ShoppingListItemStatus.ACTIVE)
                .text("text2")
                .build();
        CustomShoppingListItem itemToBeConverted2 = CustomShoppingListItem.builder()
                .id(expected2.getId())
                .status(expected2.getStatus())
                .text(expected2.getText())
                .build();
        CustomShoppingListItemResponseDto expected3 = CustomShoppingListItemResponseDto.builder()
                .id(3L)
                .status(ShoppingListItemStatus.DISABLED)
                .text("some text")
                .build();
        CustomShoppingListItem itemToBeConverted3 = CustomShoppingListItem.builder()
                .id(expected3.getId())
                .status(expected3.getStatus())
                .text(expected3.getText())
                .build();
        List<CustomShoppingListItem> listToBeConverted = new ArrayList<>();
        listToBeConverted.add(itemToBeConverted1);
        listToBeConverted.add(itemToBeConverted2);
        listToBeConverted.add(itemToBeConverted3);

        List<CustomShoppingListItemResponseDto> expectedList = new ArrayList<>();
        expectedList.add(expected1);
        expectedList.add(expected2);
        expectedList.add(expected3);

        List<CustomShoppingListItemResponseDto> actual = mapper.mapAllToList(listToBeConverted);

        assertEquals(expectedList.size(), actual.size());
        assertTrue(actual.containsAll(expectedList));
    }
}