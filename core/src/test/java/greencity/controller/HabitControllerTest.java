package greencity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import greencity.converters.UserArgumentResolver;
import greencity.dto.PageableDto;
import greencity.dto.habit.AddCustomHabitDtoRequest;
import greencity.dto.habit.AddCustomHabitDtoResponse;
import greencity.dto.habit.HabitDto;
import greencity.dto.shoppinglistitem.ShoppingListItemDto;
import greencity.dto.user.UserProfilePictureDto;
import greencity.dto.user.UserVO;
import greencity.exception.exceptions.BadRequestException;
import greencity.exception.exceptions.NotFoundException;
import greencity.exception.handler.CustomExceptionHandler;
import greencity.service.HabitService;
import greencity.service.TagsService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;

import static greencity.ModelUtils.getPrincipal;
import static greencity.ModelUtils.getUserVO;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class HabitControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HabitService habitService;

    @Mock
    private TagsService tagsService;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ObjectMapper objectMapper;

    private final Principal principal = getPrincipal();

    private final ErrorAttributes errorAttributes = new DefaultErrorAttributes();

    @InjectMocks
    private HabitController habitController;

    private static final String habitLink = "/habit";

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(habitController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(),
                        new UserArgumentResolver(userService, modelMapper))
                .setControllerAdvice(new CustomExceptionHandler(errorAttributes, objectMapper))
                .build();
    }

    @Test
    void getHabitById_ReturnsOk() throws Exception {
        mockMvc.perform(get(habitLink + "/{habitId}", 1))
                .andExpect(status().isOk());

        verify(habitService).getByIdAndLanguageCode(1L, "en");
    }

    @Test
    void getHabitById_ReturnsNotFound() throws Exception {

        when(habitService.getByIdAndLanguageCode(eq(-1L), eq("en"))).thenThrow(NotFoundException.class);

        mockMvc.perform(get(habitLink + "/{habitId}", -1L)).andExpect(status().isNotFound());

        verify(habitService).getByIdAndLanguageCode(eq(-1L), eq("en"));
    }

    @Test
    void getHabitById_ReturnsBadRequest() throws Exception {

        when(habitService.getByIdAndLanguageCode(eq(1L), eq("en"))).thenThrow(BadRequestException.class);

        mockMvc.perform(get(habitLink + "/{habitId}", 1L)).andExpect(status().isBadRequest());

        verify(habitService).getByIdAndLanguageCode(eq(1L), eq("en"));
    }

    @Test
    void getAllHabits_ReturnsOk() throws Exception {

        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        mockMvc.perform(get(habitLink)
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .param("locale", "en")
                        .principal(principal)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(habitService, times(1))
                .getAllHabitsByLanguageCode(eq(userVO), any(Pageable.class), eq("en"));
    }

    @Test
    void getShoppingListItemsById_ReturnsOk() throws Exception {

        ShoppingListItemDto shoppingListItemDto = ShoppingListItemDto.builder()
                .id(1L)
                .text("item")
                .status("status")
                .build();
        List<ShoppingListItemDto> shoppingListItems = Collections.singletonList(shoppingListItemDto);

        when(habitService.getShoppingListForHabit(anyLong(), anyString())).thenReturn(shoppingListItems);

        mockMvc.perform(get(habitLink + "/{habitId}/shopping-list", 1)
                        .param("locale", "en")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(habitService, times(1)).getShoppingListForHabit(1L, "en");
    }

    @Test
    void findALlTags_ReturnsOk() throws Exception {

        String language = "en";

        mockMvc.perform(get(habitLink + "/tags?lang=" + language))
                .andExpect(status().isOk());

        verify(tagsService, times(1)).findAllHabitsTags(language);
    }

    @Test
    void getFriendsAssignedToHabitProfilePictures_ReturnsOk() throws Exception {

        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        UserProfilePictureDto userProfilePictureDto = UserProfilePictureDto.builder()
                .id(1L)
                .name("Example")
                .profilePicturePath("https://example.com/exampleImage.png")
                .build();

        List<UserProfilePictureDto> userProfilePictures = Collections.singletonList(userProfilePictureDto);

        when(habitService.getFriendsAssignedToHabitProfilePictures(eq(1L), eq(userVO.getId()))).thenReturn(userProfilePictures);

        mockMvc.perform(get(habitLink + "/{habitId}/friends/profile-pictures", 1)
                        .principal(principal)
                        .param("userId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(habitService, times(1)).getFriendsAssignedToHabitProfilePictures(1L, userVO.getId());
    }

    @Test
    void getAllByTagsAndLanguageCode_ReturnsOk() throws Exception {
        int pageNumber = 0;
        int pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<String> tags = Collections.singletonList("tag");
        String language = "en";

        mockMvc.perform(get(habitLink + "/tags/search")
                        .param("tags", "tag")
                        .param("locale", "en")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(habitService, times(1)).getAllByTagsAndLanguageCode(eq(pageable), eq(tags), eq(language));
    }

    @Test
    void addCustomHabit_ReturnsCreated() throws Exception {

        AddCustomHabitDtoRequest request = AddCustomHabitDtoRequest.builder()
                .complexity(1)
                .defaultDuration(30)
                .habitTranslations(Collections.emptyList())
                .image("image")
                .customShoppingListItemDto(Collections.emptyList())
                .build();

        AddCustomHabitDtoResponse response = AddCustomHabitDtoResponse.builder()
                .id(1L)
                .complexity(1)
                .defaultDuration(30)
                .habitTranslations(Collections.emptyList())
                .image("image")
                .customShoppingListItemDto(Collections.emptyList())
                .build();

        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "test.png",
                MediaType.IMAGE_PNG_VALUE,
                "test".getBytes()
        );

        objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(request);

        MockMultipartFile jsonFile = new MockMultipartFile(
                "request",
                "",
                "application/json",
                requestJson.getBytes()
        );

        when(habitService.addCustomHabit(
                any(AddCustomHabitDtoRequest.class),
                any(MultipartFile.class),
                eq(principal.getName()))
        ).thenReturn(response);

        mockMvc.perform(multipart(habitLink + "/custom")
                        .file(jsonFile)
                        .file(imageFile)
                        .principal(principal)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isCreated());

        verify(habitService).addCustomHabit(
                any(AddCustomHabitDtoRequest.class),
                any(MultipartFile.class),
                eq(principal.getName())
        );
    }

    @Test
    void addCustomHabit_ReturnsBadRequest() throws Exception {

        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "test-image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        String invalidJsonRequestBody = "{" +
                "\"defaultDuration\":0," +
                "\"habitTranslations\":[{" +
                    "\"description\":\"string\"," +
                    "\"habitItem\":\"string\"," +
                    "\"languageCode\":\"string\"," +
                    "\"name\":\"string\"" +
                "}]," +
                "\"image\":\"string\"," +
                "\"customShoppingListItemDto\":[{" +
                    "\"id\":1," +
                    "\"text\":\"string\"," +
                    "\"status\":\"ACTIVE\"" +
                "}]," +
                "\"tagIds\":[0]}";  // Missing 'complexity'

        mockMvc.perform(multipart(habitLink + "/custom")
                        .file(imageFile)
                        .content(invalidJsonRequestBody)
                        .principal(principal)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addCustomHabit_ReturnsNotFound() throws Exception {

        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "test-image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        String invalidJsonRequestBody = "{" +
                "\"defaultDuration\":0," +
                "\"habitTranslations\":[{" +
                "\"description\":\"string\"," +
                "\"habitItem\":\"string\"," +
                "\"languageCode\":\"string\"," +
                "\"name\":\"string\"" +
                "}]," +
                "\"image\":\"string\"," +
                "\"customShoppingListItemDto\":[{" +
                "\"id\":1," +
                "\"text\":\"string\"," +
                "\"status\":\"ACTIVE\"" +
                "}]," +
                "\"tagIds\":[0]}";  // Missing 'complexity'

        mockMvc.perform(multipart("/custom")    // Missing 'habitLink'
                        .file(imageFile)
                        .content(invalidJsonRequestBody)
                        .principal(principal)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllByDifferentParameters_withValidParameters_ReturnOk() throws Exception {

        UserVO userVO = getUserVO();
        when(userService.findByEmail(anyString())).thenReturn(userVO);

        List<String> tags = Arrays.asList("tag1", "tag2");
        Boolean isCustomHabit = true;
        List<Integer> complexities = Arrays.asList(1, 2);

        PageableDto<HabitDto> pageableDto = new PageableDto<>(List.of(
                new HabitDto()), 1, 0, 1
        );

        when(habitService.getAllByDifferentParameters(eq(userVO), any(Pageable.class),
                any(Optional.class), any(Optional.class), any(Optional.class), anyString()))
                .thenReturn(pageableDto);

        mockMvc.perform(get(habitLink + "/search")
                        .principal(principal)
                        .param("locale", "en")
                        .param("tags", "tag1,tag2")
                        .param("isCustomHabit", "true")
                        .param("complexities", "1,2"))
                .andExpect(status().isOk());

        verify(habitService).getAllByDifferentParameters(any(UserVO.class), any(Pageable.class),
                eq(Optional.of(tags)), eq(Optional.of(isCustomHabit)), eq(Optional.of(complexities)), eq(Locale.ENGLISH.getLanguage()));
    }

    @Test
    void getAllByDifferentParameters_withNoParameters_throwBadRequestException() throws Exception {
        mockMvc.perform(get(habitLink + "/search")
                        .principal(principal))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllByDifferentParameters_withInvalidParameters_throwBadRequestException() throws Exception {
        mockMvc.perform(get(habitLink + "/search")
                        .principal(principal)
                        .param("tags", "")
                        .param("complexities", ""))
                .andExpect(status().isBadRequest());
    }

}
