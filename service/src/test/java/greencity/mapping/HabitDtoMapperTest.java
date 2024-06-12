package greencity.mapping;

import greencity.dto.habit.HabitDto;
import greencity.dto.habittranslation.HabitTranslationDto;
import greencity.dto.shoppinglistitem.ShoppingListItemDto;
import greencity.entity.*;
import greencity.entity.localization.ShoppingListItemTranslation;
import greencity.entity.localization.TagTranslation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HabitDtoMapperTest {

    @InjectMocks
    private HabitDtoMapper habitDtoMapper;

    @Test
    public void convertTest() {
        Habit habit = Habit.builder()
                .id(1L)
                .image("image")
                .defaultDuration(1)
                .complexity(1)
                .tags(new HashSet<>(Collections.singletonList(Tag.builder()
                        .tagTranslations(new ArrayList<>(Collections.singletonList(TagTranslation.builder()
                                .name("name")
                                .language(Language.builder().code("code").build())
                                .build())))
                        .build())))
                .shoppingListItems(new HashSet<>(Collections.singletonList(ShoppingListItem.builder()
                        .id(1L)
                        .translations(new ArrayList<>(Collections.singletonList(ShoppingListItemTranslation.builder()
                                .content("content")
                                .language(Language.builder().code("code").build())
                                .build())))
                        .build())))
                .build();

        HabitTranslation habitTranslation = HabitTranslation.builder()
            .description("description")
            .habitItem("habitItem")
            .name("name")
            .language(Language.builder().code("code").build())
            .habit(habit)
            .build();

        HabitDto actual = HabitDto.builder()
            .id(1L)
            .image("image")
            .defaultDuration(1)
            .complexity(1)
            .habitTranslation(HabitTranslationDto.builder()
                .description("description")
                .habitItem("habitItem")
                .name("name")
                .languageCode("code")
                .build())
            .tags(Collections.singletonList("name"))
            .shoppingListItems(Collections.singletonList(ShoppingListItemDto.builder()
                .id(1L)
                .status("ACTIVE")
                .text("content")
                .build()))
            .build();

        HabitDto expected = habitDtoMapper.convert(habitTranslation);

        assertEquals(expected, actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getImage(), actual.getImage());
        assertEquals(expected.getDefaultDuration(), actual.getDefaultDuration());
        assertEquals(expected.getComplexity(), actual.getComplexity());
        assertEquals(expected.getHabitTranslation(), actual.getHabitTranslation());
        assertEquals(expected.getTags(), actual.getTags());
        assertEquals(expected.getShoppingListItems(), actual.getShoppingListItems());
    }
}