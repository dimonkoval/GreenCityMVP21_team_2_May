package greencity;

import greencity.dto.category.CategoryDto;
import greencity.dto.econews.AddEcoNewsDtoResponse;
import greencity.dto.econews.EcoNewsForSendEmailDto;
import greencity.dto.habit.AddCustomHabitDtoRequest;
import greencity.dto.habit.AddCustomHabitDtoResponse;
import greencity.dto.habit.UserShoppingAndCustomShoppingListsDto;
import greencity.dto.shoppinglistitem.CustomShoppingListItemResponseDto;
import greencity.dto.tag.TagUaEnDto;
import greencity.dto.user.EcoNewsAuthorDto;
import greencity.dto.user.PlaceAuthorDto;
import greencity.dto.user.UserShoppingListItemResponseDto;
import greencity.dto.user.UserVO;
import greencity.dto.verifyemail.VerifyEmailVO;
import greencity.enums.Role;
import greencity.enums.ShoppingListItemStatus;
import greencity.message.AddEcoNewsMessage;
import greencity.message.SendHabitNotification;
import greencity.message.SendReportEmailMessage;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ModelUtils {
    public static UserVO getUserVO() {
        return UserVO.builder()
            .id(1L)
            .email(TestConst.EMAIL)
            .name(TestConst.NAME)
            .role(Role.ROLE_USER)
            .lastActivityTime(LocalDateTime.now())
            .verifyEmail(new VerifyEmailVO())
            .dateOfRegistration(LocalDateTime.now())
            .build();
    }

    private static PlaceAuthorDto getPlaceAuthorDto() {
        return PlaceAuthorDto.builder()
            .id(1L)
            .email("test@gmail.com")
            .name("taras")
            .build();
    }

    public static SendHabitNotification getSendHabitNotification() {
        return SendHabitNotification.builder()
            .email("test@gmail.com")
            .name("taras")
            .build();
    }

    public static CategoryDto getCategoryDto() {
        return CategoryDto.builder()
            .name("name")
            .parentCategoryId(1L)
            .build();
    }

    public static SendReportEmailMessage getSendReportEmailMessage() {
        return SendReportEmailMessage.builder()
            .emailNotification("notification")
            .subscribers(Collections.singletonList(getPlaceAuthorDto()))
            .build();
    }

    public static AddEcoNewsDtoResponse getAddEcoNewsDtoResponse() {
        return new AddEcoNewsDtoResponse(1L, "title",
            "text", "shortInfo", EcoNewsAuthorDto.builder().id(1L).name(TestConst.NAME).build(),
            ZonedDateTime.now(), TestConst.SITE, null,
            Arrays.asList("Новини", "News", "Новины"));
    }

    public static AddEcoNewsMessage getAddEcoNewsMessage() {
        return AddEcoNewsMessage.builder()
            .addEcoNewsDtoResponse(getAddEcoNewsDtoResponse())
            .build();
    }

    public static EcoNewsForSendEmailDto getEcoNewsForSendEmailDto() {
        return EcoNewsForSendEmailDto.builder()
            .unsubscribeToken("string")
            .creationDate(ZonedDateTime.now())
            .imagePath("string")
            .author(ModelUtils.getPlaceAuthorDto())
            .text("string")
            .source("string")
            .title("string")
            .build();
    }

    public static TagUaEnDto tagUaEnDto = TagUaEnDto.builder().id(1L).nameUa("Сщціальний").nameEn("Social").build();

    public static UserShoppingListItemResponseDto getUserShoppingListItemResponseDto() {
        return UserShoppingListItemResponseDto.builder()
            .id(1L)
            .text("text")
            .status(ShoppingListItemStatus.ACTIVE)
            .build();
    }

    public static CustomShoppingListItemResponseDto getCustomShoppingListItemResponseDto() {
        return CustomShoppingListItemResponseDto.builder()
            .id(1L)
            .text("text")
            .status(ShoppingListItemStatus.ACTIVE)
            .build();
    }

    public static UserShoppingAndCustomShoppingListsDto getUserShoppingAndCustomShoppingListsDto() {
        return UserShoppingAndCustomShoppingListsDto.builder()
            .userShoppingListItemDto(List.of(getUserShoppingListItemResponseDto()))
            .customShoppingListItemDto(List.of(getCustomShoppingListItemResponseDto()))
            .build();
    }

    public static AddCustomHabitDtoRequest getAddCustomHabitDtoRequest() {
        return AddCustomHabitDtoRequest.builder()
            .complexity(1)
            .image("")
            .defaultDuration(14)
            .tagIds(Set.of(20L))
            .build();
    }

    public static AddCustomHabitDtoResponse getAddCustomHabitDtoResponse() {
        return AddCustomHabitDtoResponse.builder()
            .id(1L)
            .complexity(1)
            .image("")
            .defaultDuration(14)
            .tagIds(Set.of(20L))
            .build();
    }
}
