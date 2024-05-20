package greencity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import greencity.config.SecurityConfig;
import greencity.converters.UserArgumentResolver;
import greencity.dto.habitstatistic.AddHabitStatisticDto;
import greencity.dto.habitstatistic.UpdateHabitStatisticDto;
import greencity.dto.user.UserVO;
import greencity.exception.exceptions.NotFoundException;
import greencity.exception.handler.CustomExceptionHandler;
import greencity.service.HabitStatisticService;
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
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.Locale;

import static greencity.ModelUtils.getPrincipal;
import static greencity.ModelUtils.getUserVO;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@ContextConfiguration
@Import({SecurityConfig.class, CustomExceptionHandler.class})
class HabitStatisticControllerTest {
    private MockMvc mockMvc;
    private static final String specificationLink = "/habit/statistic";

    @InjectMocks
    private HabitStatisticController habitStatisticController;

    @Mock
    private HabitStatisticService habitStatisticService;

    @Mock
    private ObjectMapper objectMapper;

    private ErrorAttributes errorAttributes = new DefaultErrorAttributes();

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    private Principal principal = getPrincipal();

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(habitStatisticController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(),
                        new UserArgumentResolver(userService, modelMapper))
                .setControllerAdvice(new CustomExceptionHandler(errorAttributes, objectMapper))
                .build();
    }

    @Test
    void findAllByHabitId_isOk() throws Exception {
        mockMvc.perform(get(specificationLink + "/{habitId}", 1)).andExpect(status().isOk());
        verify(habitStatisticService).findAllStatsByHabitId(1L);
    }

    @Test
    void findAllByHabitId_isNotFound() throws Exception {
        when(habitStatisticService.findAllStatsByHabitId(-1L)).thenThrow(NotFoundException.class);
        mockMvc.perform(get(specificationLink + "/{habitId}", -1)).andExpect(status().isNotFound());
        verify(habitStatisticService).findAllStatsByHabitId(-1L);
    }

    @Test
    void findAllStatsByHabitAssignId_isOk() throws Exception {
        mockMvc.perform(get(specificationLink + "/assign/{habitAssignId}", 1)).andExpect(status().isOk());
        verify(habitStatisticService).findAllStatsByHabitAssignId(1L);
    }

    @Test
    void findAllStatsByHabitAssignId_isNotFound() throws Exception {
        when(habitStatisticService.findAllStatsByHabitAssignId(-1L)).thenThrow(NotFoundException.class);
        mockMvc.perform(get(specificationLink + "/assign/{habitAssignId}", -1)).andExpect(status().isNotFound());
        verify(habitStatisticService).findAllStatsByHabitAssignId(-1L);
    }

    @Test
    void saveHabitStatistic_isCreated() throws Exception {
        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);
        when(modelMapper.map(userVO, UserVO.class)).thenReturn(userVO);

        String content = "{\n"
                + "  \"amountOfItems\": 1,\n"
                + "  \"habitRate\": \"DEFAULT\",\n"
                + "  \"createDate\": \"2024-05-11T15:30:45.123456789+03:00\"\n"
                + "}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        AddHabitStatisticDto addHabitStatisticDto = mapper.readValue(content, AddHabitStatisticDto.class);

        mockMvc.perform(post(specificationLink + "/{habitId}", 1)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated());
        verify(userService).findByEmail("test@gmail.com");
        verify(habitStatisticService).saveByHabitIdAndUserId(1L, userVO.getId(), addHabitStatisticDto);
    }

    @Test
    void saveHabitStatistic_isBadRequest() throws Exception {
        String content = "{\n"
                + "  \"amountOfItems\": 1\n"
                + "}";

        mockMvc.perform(post(specificationLink + "/{habitId}", 1)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(habitStatisticService);
    }

    @Test
    void saveHabitStatistic_isNotFound() throws Exception {
        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);
        when(modelMapper.map(userVO, UserVO.class)).thenReturn(userVO);

        String content = "{\n"
                + "  \"amountOfItems\": 1,\n"
                + "  \"habitRate\": \"DEFAULT\",\n"
                + "  \"createDate\": \"2024-05-11T15:30:45.123456789+03:00\"\n"
                + "}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        AddHabitStatisticDto addHabitStatisticDto = mapper.readValue(content, AddHabitStatisticDto.class);
        when(habitStatisticService.saveByHabitIdAndUserId(-1L, userVO.getId(), addHabitStatisticDto)).thenThrow(NotFoundException.class);

        mockMvc.perform(post(specificationLink + "/{habitId}", -1)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isNotFound());
        verify(userService).findByEmail("test@gmail.com");
        verify(habitStatisticService).saveByHabitIdAndUserId(-1L, userVO.getId(), addHabitStatisticDto);
    }

    @Test
    void updateStatistic_isOk() throws Exception {
        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);
        when(modelMapper.map(userVO, UserVO.class)).thenReturn(userVO);

        String content = "{\n"
                + "  \"amountOfItems\": 2,\n"
                + "  \"habitRate\": \"DEFAULT\"\n"
                + "}";
        ObjectMapper mapper = new ObjectMapper();
        UpdateHabitStatisticDto updateHabitStatisticDto = mapper.readValue(content, UpdateHabitStatisticDto.class);

        mockMvc.perform(put(specificationLink + "/{id}", 1)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
        verify(userService).findByEmail("test@gmail.com");
        verify(habitStatisticService).update(1L, userVO.getId(), updateHabitStatisticDto);
    }

    @Test
    void updateStatistic_isBadRequest() throws Exception {
        String content = "{\n"
                + "  \"amountOfItems\": 2\n"
                + "}";

        mockMvc.perform(put(specificationLink + "/{id}", 1)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(habitStatisticService);
    }

    @Test
    void updateStatistic_isNotFound() throws Exception {
        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);
        when(modelMapper.map(userVO, UserVO.class)).thenReturn(userVO);

        String content = "{\n"
                + "  \"amountOfItems\": 2,\n"
                + "  \"habitRate\": \"DEFAULT\"\n"
                + "}";
        ObjectMapper mapper = new ObjectMapper();
        UpdateHabitStatisticDto updateHabitStatisticDto = mapper.readValue(content, UpdateHabitStatisticDto.class);
        when(habitStatisticService.update(-1L, userVO.getId(), updateHabitStatisticDto)).thenThrow(NotFoundException.class);

        mockMvc.perform(put(specificationLink + "/{id}", -1)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isNotFound());
        verify(userService).findByEmail("test@gmail.com");
        verify(habitStatisticService).update(-1L, userVO.getId(), updateHabitStatisticDto);
    }

    @Test
    void getTodayStatisticsForAllHabitItems_isOk() throws Exception {
        Locale locale = Locale.getDefault();
        mockMvc.perform(get(specificationLink + "/todayStatisticsForAllHabitItems"))
                .andExpect(status().isOk());
        verify(habitStatisticService).getTodayStatisticsForAllHabitItems(locale.getLanguage());
    }

    @Test
    void findAmountOfAcquiredHabits_isOk() throws Exception {
        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);
        when(modelMapper.map(userVO, UserVO.class)).thenReturn(userVO);

        mockMvc.perform(get(specificationLink + "/acquired/count")
                        .principal(principal)
                        .param("userId", userVO.getId().toString()))
                .andExpect(status().isOk());
        verify(habitStatisticService).getAmountOfAcquiredHabitsByUserId(userVO.getId());
    }

    @Test
    void findAmountOfHabitsInProgress_isOk() throws Exception {
        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);
        when(modelMapper.map(userVO, UserVO.class)).thenReturn(userVO);
        mockMvc.perform(get(specificationLink + "/in-progress/count")
                        .principal(principal)
                        .param("userId", userVO.getId().toString()))
                .andExpect(status().isOk());
        verify(habitStatisticService).getAmountOfHabitsInProgressByUserId(userVO.getId());
    }
}