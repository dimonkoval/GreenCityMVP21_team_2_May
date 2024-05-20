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
class CustomShoppingListMapperTest {
    @InjectMocks
    CustomShoppingListMapper mapper;

    @Test
    void convert() {
        CustomShoppingListItem expected = ModelUtils.getCustomShoppingListItem();
        CustomShoppingListItemResponseDto dtoToBeConverted = CustomShoppingListItemResponseDto.builder()
                .id(expected.getId())
                .text(expected.getText())
                .status(expected.getStatus())
                .build();

        assertEquals(expected, mapper.convert(dtoToBeConverted));
    }

    @Test
    void mapAllToList() {
        CustomShoppingListItem expected1 = ModelUtils.getCustomShoppingListItem();
        CustomShoppingListItemResponseDto dtoToBeConverted1 = CustomShoppingListItemResponseDto.builder()
                .id(expected1.getId())
                .text(expected1.getText())
                .status(expected1.getStatus())
                .build();
        CustomShoppingListItem expected2 = CustomShoppingListItem.builder()
                .id(2L)
                .text("text2")
                .status(ShoppingListItemStatus.DISABLED)
                .build();
        CustomShoppingListItemResponseDto dtoToBeConverted2 = CustomShoppingListItemResponseDto.builder()
                .id(expected2.getId())
                .text(expected2.getText())
                .status(expected2.getStatus())
                .build();
        CustomShoppingListItem expected3 = CustomShoppingListItem.builder()
                .id(3L)
                .text("some text")
                .status(ShoppingListItemStatus.DONE)
                .build();
        CustomShoppingListItemResponseDto dtoToBeConverted3 = CustomShoppingListItemResponseDto.builder()
                .id(expected3.getId())
                .text(expected3.getText())
                .status(expected3.getStatus())
                .build();
        List<CustomShoppingListItemResponseDto> listToBeConverted = new ArrayList<>();
        listToBeConverted.add(dtoToBeConverted1);
        listToBeConverted.add(dtoToBeConverted2);
        listToBeConverted.add(dtoToBeConverted3);

        List<CustomShoppingListItem> expectedList = new ArrayList<>();
        expectedList.add(expected1);
        expectedList.add(expected2);
        expectedList.add(expected3);

        List<CustomShoppingListItem> actual = mapper.mapAllToList(listToBeConverted);
        assertEquals(expectedList.size(), actual.size());
        assertTrue(actual.containsAll(expectedList));
    }
}