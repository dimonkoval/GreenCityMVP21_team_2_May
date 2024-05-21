package greencity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import greencity.converters.UserArgumentResolver;
import greencity.dto.shoppinglistitem.ShoppingListItemRequestDto;
import greencity.dto.user.UserShoppingListItemResponseDto;
import greencity.dto.user.UserVO;
import greencity.exception.handler.CustomExceptionHandler;
import greencity.service.ShoppingListItemService;
import greencity.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static greencity.ModelUtils.getPrincipal;
import static greencity.ModelUtils.getUserVO;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ShoppingListItemControllerTest {

    private static final String shoppingListLink = "/user/shopping-list-items";
    private MockMvc mockMvc;

    @InjectMocks
    private ShoppingListItemController shoppingListItemController;

    @Mock
    private ShoppingListItemService shoppingListItemService;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Validator mockValidator;

    private final ErrorAttributes errorAttributes = new DefaultErrorAttributes();

    private final Principal principal = getPrincipal();

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(shoppingListItemController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(),
                        new UserArgumentResolver(userService, modelMapper))
                .setControllerAdvice(new CustomExceptionHandler(errorAttributes, objectMapper))
                .setValidator(mockValidator)
                .build();
    }

    @Test
    void bulkDeleteUserShoppingListItemTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(shoppingListItemService.deleteUserShoppingListItems(anyString())).thenReturn(ids);

        mockMvc.perform(delete(shoppingListLink + "/user-shopping-list-items")
                        .param("ids", "1,2"))
                .andExpect(status().isOk());

        verify(shoppingListItemService, times(1)).deleteUserShoppingListItems(anyString());
    }

    @Test
    void updateUserShoppingListItemStatusWithLanguageParamTest() throws Exception {
        UserVO userVO = getUserVO();
        String language = "en";

        when(userService.findByEmail(anyString())).thenReturn(userVO);

        mockMvc.perform(patch(shoppingListLink + "/{userShoppingListItemId}", 1)
                        .param("lang", language)
                        .principal(principal))
                .andExpect(status().isCreated());

        verify(shoppingListItemService, times(1))
                .updateUserShopingListItemStatus(userVO.getId(), 1L, language);
    }

    @Test
    void updateUserShoppingListItemStatus() throws Exception {
        UserVO userVO = getUserVO();
        String language = "en";

        when(userService.findByEmail(anyString())).thenReturn(userVO);

        mockMvc.perform(patch(shoppingListLink + "/{userShoppingListItemId}/status/{status}", 1, "DONE")
                        .principal(principal))
                .andExpect(status().isOk());

        verify(shoppingListItemService, times(1))
                .updateUserShoppingListItemStatus(userVO.getId(), 1L, language, "DONE");
    }

    @Test
    void updateUserShoppingListItemStatusWithLocale() throws Exception {
        UserVO userVO = getUserVO();
        String language = "en";

        when(userService.findByEmail(anyString())).thenReturn(userVO);

        mockMvc.perform(patch(shoppingListLink + "/{userShoppingListItemId}/status/{status}", 1, "DONE")
                        .param("lang", language)
                        .principal(principal))
                .andExpect(status().isOk());

        verify(shoppingListItemService, times(1))
                .updateUserShoppingListItemStatus(userVO.getId(), 1L, language, "DONE");
    }


    @Test
    void updateUserShoppingListItemStatusWithoutLanguageParamTest() throws Exception {
        UserVO userVO = getUserVO();
        String language = "en";

        when(userService.findByEmail(anyString())).thenReturn(userVO);

        mockMvc.perform(patch(shoppingListLink + "/{userShoppingListItemId}", 1)
                        .principal(principal))
                .andExpect(status().isCreated());

        verify(shoppingListItemService, times(1))
                .updateUserShopingListItemStatus(userVO.getId(), 1L, language);
    }

    @Test
    void saveUserShoppingListItemWithoutLanguageParamTest() throws Exception {
        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        String localeDefault = Locale.ENGLISH.getLanguage();
        Long habitId = 1L;

        ShoppingListItemRequestDto shoppingListItemRequestDto = ShoppingListItemRequestDto.builder().id(1L).build();
        UserShoppingListItemResponseDto userShoppingListItemResponseDto = UserShoppingListItemResponseDto.builder().build();

        when(shoppingListItemService.saveUserShoppingListItems(userVO.getId(), habitId, List.of(shoppingListItemRequestDto), localeDefault))
                .thenReturn(List.of(userShoppingListItemResponseDto));


        String requestBody = "[{\"id\":1}]";

        mockMvc.perform(post(shoppingListLink)
                        .header("Accept-Language", localeDefault)
                        .param("habitId", habitId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(principal)
                        .content(requestBody))
                .andExpect(status().isCreated());

        verify(shoppingListItemService).saveUserShoppingListItems(userVO.getId(), habitId, List.of(shoppingListItemRequestDto), localeDefault);
    }

    @Test
    void getUserShoppingListItemsWithLanguageParamTest() throws Exception {
        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        mockMvc.perform(get(shoppingListLink + "/habits/{habitId}/shopping-list", 1)
                        .param("lang", "en")
                        .principal(principal))
                .andExpect(status().isOk());

        verify(shoppingListItemService, times(1))
                .getUserShoppingList(userVO.getId(), 1L, "en");
    }

    @Test
    void getUserShoppingListItemWithoutLanguageParamTest() throws Exception {
        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        mockMvc.perform(get(shoppingListLink + "/habits/{habitId}/shopping-list", 1)
                        .principal(principal))
                .andExpect(status().isOk());

        verify(shoppingListItemService, times(1))
                .getUserShoppingList(userVO.getId(), 1L, "en");
    }

    @Test
    void deleteTest() throws Exception {
        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        mockMvc.perform(delete(shoppingListLink)
                        .param("habitId", "1")
                        .param("shoppingListItemId", "1")
                        .principal(principal))
                .andExpect(status().isOk());

        verify(shoppingListItemService, times(1))
                .deleteUserShoppingListItemByItemIdAndUserIdAndHabitId(1L, userVO.getId(), 1L);
    }

    @Test
    void findAllByUserTest() throws Exception {
        mockMvc.perform(get(shoppingListLink + "/{userId}/get-all-inprogress", 1)
                        .param("lang", "en"))
                .andExpect(status().isOk());

        verify(shoppingListItemService, times(1)).findInProgressByUserIdAndLanguageCode(1L, "en");
    }
}
