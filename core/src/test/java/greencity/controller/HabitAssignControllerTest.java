package greencity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import greencity.config.SecurityConfig;
import greencity.converters.UserArgumentResolver;
import greencity.dto.habit.*;
import greencity.dto.shoppinglistitem.CustomShoppingListItemResponseDto;
import greencity.dto.shoppinglistitem.ShoppingListItemDto;
import greencity.dto.user.UserShoppingListItemAdvanceDto;
import greencity.dto.user.UserShoppingListItemResponseDto;
import greencity.dto.user.UserVO;
import greencity.enums.HabitAssignStatus;
import greencity.exception.handler.CustomExceptionHandler;
import greencity.service.HabitAssignService;
import greencity.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static greencity.ModelUtils.getPrincipal;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration
@Import({SecurityConfig.class})
public class HabitAssignControllerTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private HabitAssignService habitAssignService;

    @InjectMocks
    private HabitAssignController habitAssignController;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    private final Principal principal = getPrincipal();

    private final ErrorAttributes errorAttributes = new DefaultErrorAttributes();
    private HabitAssignManagementDto habitAssignManagementDto;
    private List<HabitAssignManagementDto> habitAssignManagementDtoList;
    private HabitAssignUserDurationDto habitAssignUserDurationDto;
    private HabitAssignDto habitAssignDto;
    private List<HabitAssignDto> habitAssignDtoList;
    private UserShoppingAndCustomShoppingListsDto userShoppingAndCustomShoppingListsDto;
    private List<UserShoppingAndCustomShoppingListsDto> userShoppingAndCustomShoppingListsDtoList;
    private HabitDto habitDto;
    private HabitAssignStatDto habitAssignStatDto;
    private List<HabitsDateEnrollmentDto> habitsDateEnrollmentDtoList;
    private UpdateUserShoppingListDto updateUserShoppingListDto;

    @BeforeEach
    public void setUp() {
        habitAssignManagementDto = HabitAssignManagementDto.builder()
                .id(1L)
                .status(HabitAssignStatus.INPROGRESS)
                .createDateTime(null)
                .habitId(1L)
                .userId(1L)
                .duration(1)
                .workingDays(1)
                .habitStreak(1)
                .lastEnrollment(null)
                .progressNotificationHasDisplayed(false)
                .build();

        habitAssignManagementDtoList = List.of(habitAssignManagementDto);

        habitAssignUserDurationDto = HabitAssignUserDurationDto.builder()
                .habitAssignId(1L)
                .userId(1L)
                .habitId(1L)
                .status(HabitAssignStatus.INPROGRESS)
                .workingDays(1)
                .duration(1)
                .build();

        habitAssignDto = HabitAssignDto.builder()
                .createDateTime(null)
                .duration(1)
                .habit(null)
                .userShoppingListItems(null)
                .habitStatusCalendarDtoList(null)
                .habitStreak(1)
                .id(1L)
                .lastEnrollmentDate(null)
                .status(HabitAssignStatus.INPROGRESS)
                .userId(1L)
                .workingDays(1)
                .progressNotificationHasDisplayed(false)
                .build();

        habitAssignDtoList = List.of(habitAssignDto);

        userShoppingAndCustomShoppingListsDto = UserShoppingAndCustomShoppingListsDto.builder()
                .userShoppingListItemDto(List.of(new UserShoppingListItemResponseDto()))
                .customShoppingListItemDto(List.of(new CustomShoppingListItemResponseDto()))
                .build();

        userShoppingAndCustomShoppingListsDtoList = List.of(userShoppingAndCustomShoppingListsDto);

        habitDto = HabitDto.builder()
                .id(1L)
                .defaultDuration(1)
                .amountAcquiredUsers(1L)
                .habitTranslation(null)
                .image("image")
                .complexity(1)
                .tags(List.of("tag"))
                .shoppingListItems(List.of(new ShoppingListItemDto()))
                .customShoppingListItems(List.of(new CustomShoppingListItemResponseDto()))
                .isCustomHabit(false)
                .usersIdWhoCreatedCustomHabit(1L)
                .habitAssignStatus(HabitAssignStatus.INPROGRESS)
                .build();

        habitAssignStatDto = HabitAssignStatDto.builder()
                .status(HabitAssignStatus.INPROGRESS)
                .build();

        habitsDateEnrollmentDtoList = List.of(new HabitsDateEnrollmentDto());

        updateUserShoppingListDto = UpdateUserShoppingListDto.builder()
                .habitAssignId(1L)
                .userShoppingListItemId(1L)
                .userShoppingListAdvanceDto(List.of(new UserShoppingListItemAdvanceDto()))
                .build();

        mockMvc = MockMvcBuilders.standaloneSetup(habitAssignController)
                .setControllerAdvice(new CustomExceptionHandler(errorAttributes, objectMapper))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(),
                        new UserArgumentResolver(userService, modelMapper))
                .build();
    }

    @Test
    public void assignDefaultTest_ReturnsIsCreated() throws Exception {
        given(habitAssignService.assignDefaultHabitForUser(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .willReturn(habitAssignManagementDto);

        ResultActions response = mockMvc.perform(post("/habit/assign/{habitId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(habitAssignManagementDto)));

        response.andExpect(status().isCreated());
    }

    @Test
    public void assignCustomTest_ReturnsIsCreated() throws Exception {
        given(habitAssignService.assignCustomHabitForUser(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .willReturn(habitAssignManagementDtoList);

        ResultActions response = mockMvc.perform(post("/habit/assign/{habitId}/custom", 1L)
                .content("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"status\": \"INPROGRESS\",\n" +
                        "  \"createDateTime\": null,\n" +
                        "  \"habitId\": 1,\n" +
                        "  \"userId\": 1,\n" +
                        "  \"duration\": 1,\n" +
                        "  \"workingDays\": 1,\n" +
                        "  \"habitStreak\": 1,\n" +
                        "  \"lastEnrollment\": null,\n" +
                        "  \"progressNotificationHasDisplayed\": false\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isCreated());
    }

    @Test
    public void updateHabitAssignDurationTest_ReturnsIsOk() throws Exception {
        Long habitAssignId = 1L;
        Long userId = 1L;
        Integer duration = 1;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        when(habitAssignService.updateUserHabitInfoDuration(habitAssignId, userVO.getId(), duration))
                .thenReturn(habitAssignUserDurationDto);

        ResultActions response = mockMvc.perform(put("/habit/assign/{habitAssignId}/update-habit-duration",
                habitAssignId)
                .principal(principal)
                .content("{\n" +
                        "  \"habitAssignId\": 1,\n" +
                        "  \"userId\": 1,\n" +
                        "  \"habitId\": 1,\n" +
                        "  \"status\": \"INPROGRESS\",\n" +
                        "  \"workingDays\": 1,\n" +
                        "  \"duration\": 1\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .param("duration", duration.toString()));

        response.andExpect(status().isOk());
    }

    @Test
    public void getHabitAssignTest_ReturnsIsOk() throws Exception {
        Locale locale = Locale.ENGLISH;
        Long userId = 1L;
        Long habitAssignId = 1L;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        when(habitAssignService.getByHabitAssignIdAndUserId(habitAssignId, userVO.getId(), locale.getLanguage()))
                .thenReturn(habitAssignDto);

        ResultActions response = mockMvc.perform(get("/habit/assign/{habitAssignId}", habitAssignId)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .param("locale", String.valueOf(locale.getLanguage())));

        response.andExpect(status().isOk());
    }

    @Test
    public void getCurrentUserHabitAssignsByIdAndAcquiredTest_ReturnsIsOk() throws Exception {
        Locale locale = Locale.ENGLISH;
        Long userId = 1L;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        when(habitAssignService.getAllHabitAssignsByUserIdAndStatusNotCancelled(userVO.getId(), locale.getLanguage()))
                .thenReturn(habitAssignDtoList);

        ResultActions response = mockMvc.perform(get("/habit/assign/allForCurrentUser")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .param("locale", String.valueOf(locale.getLanguage())));

        response.andExpect(status().isOk());
    }

    @Test
    public void getUserShoppingAndCustomShoppingListsTest_ReturnsIsOk() throws Exception {
        Locale locale = Locale.ENGLISH;
        Long userId = 1L;
        Long habitAssignId = 1L;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        when(habitAssignService.getUserShoppingAndCustomShoppingLists(userVO.getId(), habitAssignId, locale.getLanguage()))
                .thenReturn(userShoppingAndCustomShoppingListsDto);

        ResultActions response = mockMvc.perform(get("/habit/assign/{habitAssignId}/allUserAndCustomList",
                habitAssignId)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .param("locale", String.valueOf(locale.getLanguage())));

        response.andExpect(status().isOk());
    }

    @Test
    public void updateUserAndCustomShoppingListsTest_ReturnsIsOk() throws Exception {
        Locale locale = Locale.ENGLISH;
        Long userId = 1L;
        Long habitAssignId = 1L;


        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        doNothing().when(habitAssignService).fullUpdateUserAndCustomShoppingLists(userVO.getId(), habitAssignId, userShoppingAndCustomShoppingListsDto,
                locale.getLanguage());

        ResultActions response = mockMvc.perform(get("/habit/assign/{habitAssignId}/allUserAndCustomList", habitAssignId)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .param("locale", String.valueOf(locale.getLanguage())));

        response.andExpect(status().isOk());
    }

    @Test
    public void getListOfUserAndCustomShoppingListsInprogressTest_ReturnsIsOk() throws Exception {
        Locale locale = Locale.ENGLISH;
        Long userId = 1L;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        when(habitAssignService.getListOfUserAndCustomShoppingListsWithStatusInprogress(userVO.getId(), locale.getLanguage()))
                .thenReturn(userShoppingAndCustomShoppingListsDtoList);

        ResultActions response = mockMvc.perform(get("/habit/assign/allUserAndCustomShoppingListsInprogress")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .param("locale", String.valueOf(locale.getLanguage())));

        response.andExpect(status().isOk());
    }

    @Test
    public void getAllHabitAssignsByHabitIdAndAcquiredTest_ReturnsIsOk() throws Exception {
        Locale locale = Locale.ENGLISH;
        Long habitId = 1L;

        when(habitAssignService.getAllHabitAssignsByHabitIdAndStatusNotCancelled(habitId, locale.getLanguage()))
                .thenReturn(habitAssignDtoList);

        ResultActions response = mockMvc.perform(get("/habit/assign/{habitId}/all", habitId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("locale", String.valueOf(locale.getLanguage())));

        response.andExpect(status().isOk());
    }

    @Test
    public void getHabitAssignByHabitIdTest_ReturnsIsOk() throws Exception {
        Locale locale = Locale.ENGLISH;
        Long habitId = 1L;
        Long userId = 1L;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        when(habitAssignService.findHabitAssignByUserIdAndHabitId(userVO.getId(), habitId, locale.getLanguage()))
                .thenReturn(habitAssignDto);

        ResultActions response = mockMvc.perform(get("/habit/assign/{habitId}/active", habitId)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .param("locale", String.valueOf(locale.getLanguage())));

        response.andExpect(status().isOk());
    }

    @Test
    public void getUsersHabitByHabitAssignIdTest_ReturnsIsOk() throws Exception {
        Locale locale = Locale.ENGLISH;
        Long habitAssignId = 1L;

        UserVO userV0 = new UserVO();
        userV0.setId(1L);
        when(userService.findByEmail(anyString())).thenReturn(userV0);

        when(habitAssignService.findHabitByUserIdAndHabitAssignId(userV0.getId(), habitAssignId, locale.getLanguage()))
                .thenReturn(habitDto);

        ResultActions response = mockMvc.perform(get("/habit/assign/{habitAssignId}/more", habitAssignId)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .param("locale", String.valueOf(locale.getLanguage())));

        response.andExpect(status().isOk());
    }

    @Test
    public void updateAssignByHabitId_ReturnsIsOk() throws Exception {
        Long habitAssignId = 1L;

        when(habitAssignService.updateStatusByHabitAssignId(habitAssignId, habitAssignStatDto))
                .thenReturn(habitAssignManagementDto);

        ResultActions response = mockMvc.perform(patch("/habit/assign/{habitAssignId}", habitAssignId)
                        .content("{\n" +
                                "  \"status\": \"INPROGRESS\"\n" +
                                "}")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    public void enrollHabitTest_ReturnsIsOk() throws Exception {
        LocalDate date = LocalDate.now();
        Locale locale = Locale.ENGLISH;
        Long habitAssignId = 1L;
        Long userId = 1L;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        given(habitAssignService.enrollHabit(habitAssignId, userVO.getId(), date, locale.getLanguage()))
                .willReturn(habitAssignDto);

        ResultActions response = mockMvc.perform(post("/habit/assign/{habitAssignId}/enroll/{date}",
                habitAssignId, date)
                .principal(principal)
                .content("{\n" +
                        "  \"createDateTime\": null,\n" +
                        "  \"duration\": 1,\n" +
                        "  \"habit\": null,\n" +
                        "  \"userShoppingListItems\": null,\n" +
                        "  \"habitStatusCalendarDtoList\": null,\n" +
                        "  \"habitStreak\": 1,\n" +
                        "  \"id\": 1,\n" +
                        "  \"lastEnrollmentDate\": null,\n" +
                        "  \"status\": \"INPROGRESS\",\n" +
                        "  \"userId\": 1,\n" +
                        "  \"workingDays\": 1,\n" +
                        "  \"progressNotificationHasDisplayed\": false\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
                .param("locale", String.valueOf(locale.getLanguage())));

        response.andExpect(status().isOk());
    }

    @Test
    public void unenrollHabitTest_ReturnsIsOk() throws Exception {
        LocalDate date = LocalDate.now();
        Long habitAssignId = 1L;
        Long userId = 1L;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        given(habitAssignService.unenrollHabit(habitAssignId, userVO.getId(), date))
                .willReturn(habitAssignDto);

        ResultActions response = mockMvc.perform(post("/habit/assign/{habitAssignId}/unenroll/{date}",
                habitAssignId, date)
                .principal(principal)
                .content("{\n" +
                        "  \"createDateTime\": null,\n" +
                        "  \"duration\": 1,\n" +
                        "  \"habit\": null,\n" +
                        "  \"userShoppingListItems\": null,\n" +
                        "  \"habitStatusCalendarDtoList\": null,\n" +
                        "  \"habitStreak\": 1,\n" +
                        "  \"id\": 1,\n" +
                        "  \"lastEnrollmentDate\": null,\n" +
                        "  \"status\": \"INPROGRESS\",\n" +
                        "  \"userId\": 1,\n" +
                        "  \"workingDays\": 1,\n" +
                        "  \"progressNotificationHasDisplayed\": false\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    public void getInprogressHabitAssignOnDateTest_ReturnsIsOk() throws Exception {
        LocalDate date = LocalDate.now();
        Locale locale = Locale.ENGLISH;
        Long userId = 1L;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        when(habitAssignService.findInprogressHabitAssignsOnDate(userVO.getId(), date, locale.getLanguage()))
                .thenReturn(habitAssignDtoList);

        ResultActions response = mockMvc.perform(get("/habit/assign/active/{date}", date)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .param("locale", String.valueOf(locale.getLanguage())));

        response.andExpect(status().isOk());
    }

    @Test
    public void getHabitAssignBetweenDatesTest_ReturnsIsOk() throws Exception {
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now();
        Locale locale = Locale.ENGLISH;
        Long userId = 1L;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        when(habitAssignService.findHabitAssignsBetweenDates(userVO.getId(), from, to, locale.getLanguage()))
                .thenReturn(habitsDateEnrollmentDtoList);

        ResultActions response = mockMvc.perform(get("/habit/assign/activity/{from}/to/{to}", from, to)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .param("locale", String.valueOf(locale.getLanguage())));

        response.andExpect(status().isOk());
    }

    @Test
    public void cancelHabitAssignTest_ReturnsIsOk() throws Exception {
        Long habitId = 1L;
        Long userId = 1L;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        when(habitAssignService.cancelHabitAssign(habitId, userVO.getId()))
                .thenReturn(habitAssignDto);

        ResultActions response = mockMvc.perform(patch("/habit/assign/cancel/{habitId}", habitId)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    public void deleteHabitAssignTest_ReturnsIsOk() throws Exception {
        Long habitAssignId = 1L;
        Long userId = 1L;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        doNothing().when(habitAssignService).deleteHabitAssign(habitAssignId, userVO.getId());

        ResultActions response = mockMvc.perform(delete("/habit/assign/delete/{habitAssignId}", habitAssignId)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    public void updateShoppingListStatusTest_ReturnsIsOk() throws Exception {
        doNothing().when(habitAssignService).updateUserShoppingListItem(updateUserShoppingListDto);

        ResultActions response = mockMvc.perform(put("/habit/assign/saveShoppingListForHabitAssign")
                .content("{\n" +
                        "  \"habitAssignId\": 1,\n" +
                        "  \"userShoppingListItemId\": 1,\n" +
                        "  \"userShoppingListAdvanceDto\": [\n" +
                        "    {\n" +
                        "      \"id\": 1,\n" +
                        "      \"name\": \"name\",\n" +
                        "      \"amount\": 1,\n" +
                        "      \"status\": \"INPROGRESS\",\n" +
                        "      \"habitAssignId\": 1\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    public void updateProgressNotificationHasDisplayedTest_ReturnsIsOk() throws Exception {
        Long habitAssignId = 1L;
        Long userId = 1L;

        UserVO userVO = new UserVO();
        userVO.setId(userId);
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        doNothing().when(habitAssignService).updateProgressNotificationHasDisplayed(habitAssignId, userVO.getId());

        ResultActions response = mockMvc
                .perform(put("/habit/assign/{habitAssignId}/updateProgressNotificationHasDisplayed",
                        habitAssignId)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }
}
