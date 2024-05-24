package greencity.controller;

import static greencity.ModelUtils.getUserVO;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static greencity.ModelUtils.getCustomShoppingListItemResponseDto;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import greencity.dto.shoppinglistitem.BulkSaveCustomShoppingListItemDto;
import greencity.dto.shoppinglistitem.CustomShoppingListItemResponseDto;
import greencity.dto.shoppinglistitem.CustomShoppingListItemSaveRequestDto;
import greencity.dto.user.UserVO;
import greencity.service.CustomShoppingListItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomShoppingListItemControllerTest {
    static final String PATH_LINK = "/custom/shopping-list-items";
    static final Long TEST_ID_1L = 1L;
    static final Long TEST_ID_2L = 2L;
    static final Long HABIT_ID = 1L;
    static final Long HABIT_ASSIGN_ID = 1L;
    static final int VERIFY_TIMES_1 = 1;
    static final String STATUS_ACTIVE = "ACTIVE";
    static final String TEST_TEXT = "text";
    final UserVO USER_VO = getUserVO();
    MockMvc mockMvc;
    @InjectMocks
    CustomShoppingListItemController customShoppingListItemController;
    @Mock
    CustomShoppingListItemService customShoppingListItemService;
    @Mock
    Validator mockValidator;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(customShoppingListItemController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setValidator(mockValidator)
                .build();
    }

    @Test
    void getAllAvailableCustomShoppingListItemsTest() throws Exception {

        List<CustomShoppingListItemResponseDto> customShoppingListItems =
                List.of(getCustomShoppingListItemResponseDto());

        when(customShoppingListItemService.findAllAvailableCustomShoppingListItems(anyLong(), anyLong()))
                .thenReturn(customShoppingListItems);

        mockMvc.perform(get(String.format("%s/%d/%d", PATH_LINK, TEST_ID_1L, HABIT_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(TEST_ID_1L))
                .andExpect(jsonPath("$[0].text").value(TEST_TEXT))
                .andExpect(jsonPath("$[0].status").value(STATUS_ACTIVE));

        verify(customShoppingListItemService, times(VERIFY_TIMES_1))
                .findAllAvailableCustomShoppingListItems(anyLong(), anyLong());
    }

    @Test
    void saveUserCustomShoppingListItemsTest() throws Exception {
        List<CustomShoppingListItemSaveRequestDto> listRequestDto = Arrays.asList(
                new CustomShoppingListItemSaveRequestDto("1"),
                new CustomShoppingListItemSaveRequestDto("2")
        );
        BulkSaveCustomShoppingListItemDto requestDto = new BulkSaveCustomShoppingListItemDto();
        requestDto.setCustomShoppingListItemSaveRequestDtoList(listRequestDto);

        List<CustomShoppingListItemResponseDto> responseDtoList = List.of(getCustomShoppingListItemResponseDto());

        when(customShoppingListItemService.save(eq(requestDto), anyLong(), anyLong())).thenReturn(responseDtoList);

        mockMvc.perform(post(String.format("%s/%d/%d/custom-shopping-list-items",
                        PATH_LINK, USER_VO.getId(), HABIT_ASSIGN_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(TEST_ID_1L))
                .andExpect(jsonPath("$[0].text").value(TEST_TEXT))
                .andExpect(jsonPath("$[0].status").value(STATUS_ACTIVE));

        verify(customShoppingListItemService, times(VERIFY_TIMES_1))
                .save(eq(requestDto), anyLong(), anyLong());
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateItemStatusTest() throws Exception {

        CustomShoppingListItemResponseDto responseDto = getCustomShoppingListItemResponseDto();

        when(customShoppingListItemService.updateItemStatus(anyLong(), anyLong(), anyString()))
                .thenReturn(responseDto);

        mockMvc.perform(patch(String.format("%s/%d/custom-shopping-list-items?itemId=%d&status=%s",
                        PATH_LINK, USER_VO.getId(), TEST_ID_1L, STATUS_ACTIVE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(TEST_ID_1L))
                .andExpect(jsonPath("$.text").value(TEST_TEXT))
                .andExpect(jsonPath("$.status").value(STATUS_ACTIVE));

        verify(customShoppingListItemService, times(VERIFY_TIMES_1))
                .updateItemStatus(anyLong(), anyLong(), anyString());
    }

    @Test
    void updateItemStatusToDoneTest() throws Exception {

        mockMvc.perform(patch(String.format("%s/%d/done?itemId=%d", PATH_LINK, USER_VO.getId(), TEST_ID_1L))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customShoppingListItemService, times(VERIFY_TIMES_1))
                .updateItemStatusToDone(eq(USER_VO.getId()), eq(TEST_ID_1L));
    }

    @Test
    void bulkDeleteCustomShoppingListItemsTest() throws Exception {
        String ids = "1,2";

        List<Long> deletedIds = List.of(TEST_ID_1L, TEST_ID_2L);

        when(customShoppingListItemService.bulkDelete(eq(ids))).thenReturn(deletedIds);

        mockMvc.perform(delete(String.format("%s/%d/custom-shopping-list-items?ids=%s", PATH_LINK, USER_VO.getId(), ids))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").value(1))
                .andExpect(jsonPath("$[1]").value(2));

        verify(customShoppingListItemService, times(VERIFY_TIMES_1))
                .bulkDelete(eq(ids));
    }

    @Test
    void getAllCustomShoppingItemsByStatusTest() throws Exception {

        List<CustomShoppingListItemResponseDto> customShoppingListItems =
                List.of(getCustomShoppingListItemResponseDto());

        when(customShoppingListItemService.findAllUsersCustomShoppingListItemsByStatus(anyLong(), anyString()))
                .thenReturn(customShoppingListItems);

        mockMvc.perform(get(String.format("%s/%d/custom-shopping-list-items", PATH_LINK, USER_VO.getId()))
                        .param("status", STATUS_ACTIVE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(TEST_ID_1L))
                .andExpect(jsonPath("$[0].text").value(TEST_TEXT))
                .andExpect(jsonPath("$[0].status").value(STATUS_ACTIVE));

        verify(customShoppingListItemService, times(VERIFY_TIMES_1))
                .findAllUsersCustomShoppingListItemsByStatus(anyLong(), anyString());
    }
}