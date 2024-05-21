package greencity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import greencity.converters.UserArgumentResolver;
import greencity.dto.shoppinglistitem.ShoppingListItemDto;
import greencity.dto.shoppinglistitem.ShoppingListItemRequestDto;
import greencity.dto.user.UserShoppingListItemResponseDto;
import greencity.dto.user.UserVO;
import greencity.service.ShoppingListItemService;
import greencity.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import static greencity.ModelUtils.getPrincipal;
import static greencity.ModelUtils.getUserShoppingListItemResponseDto;
import static greencity.ModelUtils.getUserVO;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ShoppingListItemControllerTest {
    static final String PATH_LINK = "/user/shopping-list-items";
    static final Long TEST_ID_1L = 1L;
    static final Long TEST_ID_2L = 2L;
    static final Long HABIT_ID = 1L;
    static final int VERIFY_TIMES_1 = 1;
    static final Locale LOCALE_EN = Locale.forLanguageTag("en");
    static final String STATUS_ACTIVE = "ACTIVE";
    static final String TEST_TEXT = "text";
    final UserVO USER_VO = getUserVO();
    final Principal principal = getPrincipal();
    MockMvc mockMvc;
    @InjectMocks
    ShoppingListItemController shoppingListItemController;
    @Mock
    ShoppingListItemService shoppingListItemService;
    @Mock
    UserService userService;
    @Mock
    Validator mockValidator;
    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(shoppingListItemController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(),
                        new UserArgumentResolver(userService, modelMapper))
                .setValidator(mockValidator)
                .build();
    }

    @Test
    void getShoppingListItemsAssignedToUserTest() throws Exception {

        List<UserShoppingListItemResponseDto> shoppingList = List.of(getUserShoppingListItemResponseDto());

        when(userService.findByEmail(anyString())).thenReturn(USER_VO);
        when(shoppingListItemService.getUserShoppingList(eq(USER_VO.getId()), eq(HABIT_ID), eq(LOCALE_EN.toString())))
                .thenReturn(shoppingList);

        mockMvc.perform(get(String.format("%s/habits/%d/shopping-list", PATH_LINK, HABIT_ID))
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(TEST_ID_1L))
                .andExpect(jsonPath("$[0].status").value(STATUS_ACTIVE))
                .andExpect(jsonPath("$[0].text").value(TEST_TEXT));

        verify(shoppingListItemService, times(VERIFY_TIMES_1))
                .getUserShoppingList(USER_VO.getId(), TEST_ID_1L, LOCALE_EN.toString());
    }

    @Test
    void saveUserShoppingListItemsTest() throws Exception {
        List<ShoppingListItemRequestDto> shoppingListItems = Arrays.asList(
                new ShoppingListItemRequestDto(TEST_ID_1L),
                new ShoppingListItemRequestDto(TEST_ID_2L)
        );
        List<UserShoppingListItemResponseDto> shoppingList = List.of(getUserShoppingListItemResponseDto());

        when(userService.findByEmail(anyString())).thenReturn(USER_VO);
        when(shoppingListItemService
                .saveUserShoppingListItems(USER_VO.getId(), HABIT_ID, shoppingListItems, LOCALE_EN.getLanguage()))
                .thenReturn(shoppingList);

        mockMvc.perform(post(PATH_LINK)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(shoppingListItems))
                        .param("habitId", String.valueOf(HABIT_ID))
                        .param("locale", LOCALE_EN.getLanguage())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(TEST_ID_1L))
                .andExpect(jsonPath("$[0].status").value(STATUS_ACTIVE))
                .andExpect(jsonPath("$[0].text").value(TEST_TEXT));

        verify(shoppingListItemService)
                .saveUserShoppingListItems(USER_VO.getId(), HABIT_ID, shoppingListItems, LOCALE_EN.getLanguage());
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteTest() throws Exception {

        when(userService.findByEmail(anyString())).thenReturn(USER_VO);

        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_LINK)
                        .requestAttr("CurrentUser", USER_VO)
                        .param("habitId", String.valueOf(HABIT_ID))
                        .param("shoppingListItemId", String.valueOf(TEST_ID_1L))
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(shoppingListItemService).deleteUserShoppingListItemByItemIdAndUserIdAndHabitId(
                TEST_ID_1L, USER_VO.getId(), HABIT_ID);

        verify(shoppingListItemService, times(VERIFY_TIMES_1))
                .deleteUserShoppingListItemByItemIdAndUserIdAndHabitId(TEST_ID_1L, USER_VO.getId(), HABIT_ID);
    }

    @Test
    void updateUserShoppingListItemStatusTest() throws Exception {

        when(userService.findByEmail(anyString())).thenReturn(USER_VO);
        when(shoppingListItemService.updateUserShopingListItemStatus(USER_VO.getId(), TEST_ID_1L,
                LOCALE_EN.getLanguage())).thenReturn(getUserShoppingListItemResponseDto());

        mockMvc.perform(patch(String.format("%s/%d", PATH_LINK, TEST_ID_1L))
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .locale(LOCALE_EN))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.id").value(TEST_ID_1L))
                .andExpect(jsonPath("$.status").value(STATUS_ACTIVE))
                .andExpect(jsonPath("$.text").value(TEST_TEXT));

        verify(shoppingListItemService, times(VERIFY_TIMES_1))
                .updateUserShopingListItemStatus(USER_VO.getId(), TEST_ID_1L, LOCALE_EN.getLanguage());
    }

    @Test
    void testUpdateUserShoppingListItemStatusTest() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(USER_VO);

        when(shoppingListItemService
                .updateUserShoppingListItemStatus(USER_VO.getId(), TEST_ID_1L, LOCALE_EN.getLanguage(), STATUS_ACTIVE))
                .thenReturn(List.of(getUserShoppingListItemResponseDto()));

        mockMvc.perform(patch(String.format("%s/%d/status/%s", PATH_LINK, TEST_ID_1L, STATUS_ACTIVE))
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(TEST_ID_1L))
                .andExpect(jsonPath("$[0].status").value(STATUS_ACTIVE))
                .andExpect(jsonPath("$[0].text").value(TEST_TEXT));

        verify(shoppingListItemService, times(VERIFY_TIMES_1))
                .updateUserShoppingListItemStatus(USER_VO.getId(), TEST_ID_1L, LOCALE_EN.getLanguage(), STATUS_ACTIVE);
    }

    @Test
    void bulkDeleteUserShoppingListItemsTest() throws Exception {
        String ids = "1,2,3";
        when(userService.findByEmail(anyString())).thenReturn(USER_VO);
        when(shoppingListItemService
                .deleteUserShoppingListItems(ids)).thenReturn(List.of(TEST_ID_1L, TEST_ID_2L));

        mockMvc.perform(MockMvcRequestBuilders.delete(String.format("%s/user-shopping-list-items", PATH_LINK))
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("ids", ids))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").value(TEST_ID_1L))
                .andExpect(jsonPath("$[1]").value(TEST_ID_2L));

        verify(shoppingListItemService, times(VERIFY_TIMES_1)).deleteUserShoppingListItems(anyString());
    }

    @Test
    void findInProgressByUserIdTest() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(USER_VO);
        when(shoppingListItemService.findInProgressByUserIdAndLanguageCode(TEST_ID_2L, LOCALE_EN.toString()))
                .thenReturn(List.of(ShoppingListItemDto.builder().build()));

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("%s/%d/get-all-inprogress", PATH_LINK, TEST_ID_2L))
                        .principal(principal)
                        .param("lang", LOCALE_EN.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(shoppingListItemService, times(VERIFY_TIMES_1))
                .findInProgressByUserIdAndLanguageCode(TEST_ID_2L, LOCALE_EN.toString());
    }
}