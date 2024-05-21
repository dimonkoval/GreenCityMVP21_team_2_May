package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.shoppinglistitem.ShoppingListItemResponseDto;
import greencity.dto.shoppinglistitem.ShoppingListItemTranslationDTO;
import greencity.entity.ShoppingListItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ShoppingListItemResponseDtoMapperTest {
    @InjectMocks
    ShoppingListItemResponseDtoMapper mapper;

    @Test
    void convert() {
        ShoppingListItem itemToBeConverted = ModelUtils.getShoppingListItem();
        ShoppingListItemResponseDto expected = ShoppingListItemResponseDto.builder()
                .id(itemToBeConverted.getId())
                .translations(itemToBeConverted.getTranslations().stream().map(
                                shoppingListItemTranslation -> ShoppingListItemTranslationDTO.builder()
                                        .id(shoppingListItemTranslation.getId())
                                        .content(shoppingListItemTranslation.getContent())
                                        .build())
                        .collect(Collectors.toList()))
                .build();

        assertEquals(expected, mapper.convert(itemToBeConverted));
    }
}
