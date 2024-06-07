package greencity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import greencity.ModelUtils;
import greencity.config.PageableConfig;
import greencity.config.SecurityConfig;
import greencity.dto.event.EventRequestSaveDto;
import greencity.dto.user.UserVO;
import greencity.security.jwt.JwtTool;
import greencity.service.EventService;
import greencity.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SecurityConfig.class, EventsController.class, PageableConfig.class})
@WebAppConfiguration
@EnableWebMvc
@Import({JwtTool.class})
@TestPropertySource(properties = {
        "accessTokenValidTimeInMinutes=60",
        "refreshTokenValidTimeInMinutes=1440",
        "tokenKey=secretTokenKey"
})
class EventsControllerWithSecurityConfigTest {
    private static final String LINK = "/events";

    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        when(userService.findByEmail(anyString())).thenReturn(ModelUtils.getUserVO());
    }

    @Test
    @WithAnonymousUser
    void saveEvent_ReturnsIsUnauthorized() throws Exception {
        String json = "{}";

        MockMultipartFile jsonFile = new MockMultipartFile(
                "eventRequestSaveDto",
                "",
                "application/json",
                json.getBytes());

        MockMultipartFile imageFile = new MockMultipartFile(
                "images",
                "image.jpg",
                "image/jpeg",
                "image data".getBytes());

        mockMvc.perform(multipart(LINK)
                        .file(jsonFile)
                        .file(imageFile)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(eventService);

    }

    @ParameterizedTest
    @CsvSource({
            "User, USER",
            "Moderator, MODERATOR",
            "Ubs_Employee, UBS_EMPLOYEE",
            "Employee, EMPLOYEE"
    })
    @WithMockUser
    void saveEvent_ReturnsIsCreated() throws Exception {
        String json = "{\n" +
                "  \"title\": \"string\",\n" +
                "  \"daysInfo\": [\n" +
                "    {\n" +
                "      \"startDateTime\": \"2024-06-02T14:20:45.252Z\",\n" +
                "      \"endDateTime\": \"2024-06-02T14:20:45.252Z\",\n" +
                "      \"dayNumber\": 0,\n" +
                "      \"location\": \"string\",\n" +
                "      \"allDay\": true,\n" +
                "      \"online\": true\n" +
                "    }\n" +
                "  ],\n" +
                "  \"description\": \"stringstringstringst\",\n" +
                "  \"mainImageNumber\": 0,\n" +
                "  \"open\": true\n" +
                "}";

        MockMultipartFile jsonFile = new MockMultipartFile(
                "eventRequestSaveDto",
                "",
                "application/json",
                json.getBytes());

        MockMultipartFile imageFile = new MockMultipartFile(
                "images",
                "image.jpg",
                "image/jpeg",
                "image data".getBytes());

        mockMvc.perform(multipart(LINK)
                        .file(jsonFile)
                        .file(imageFile)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        EventRequestSaveDto eventRequestSaveDto = mapper.readValue(json, EventRequestSaveDto.class);

        verify(eventService).save(eq(eventRequestSaveDto), any(MultipartFile[].class), any(UserVO.class));
    }

    @ParameterizedTest
    @CsvSource({
            ",",                    // anonymous user
            "User, USER",
            "Moderator, MODERATOR",
            "Ubs_Employee, UBS_EMPLOYEE",
            "Employee, EMPLOYEE"
    })
    @WithMockUser
    void findAllEventsTest_ReturnsIsOk() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);

        mockMvc.perform(get(LINK)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(eventService).findAll(pageable);
    }

    @ParameterizedTest
    @CsvSource({
            ",",                    // anonymous user
            "User, USER",
            "Moderator, MODERATOR",
            "Ubs_Employee, UBS_EMPLOYEE",
            "Employee, EMPLOYEE"
    })
    @WithMockUser
    void getEventByIdTest_ReturnsIsOk() throws Exception {
        mockMvc.perform(get(LINK + "/{id}", 1L))
                .andExpect(status().isOk());

        verify(eventService).findById(1L);
    }

    @ParameterizedTest
    @CsvSource({
            ",",                    // anonymous user
            "User, USER",
            "Moderator, MODERATOR",
            "Ubs_Employee, UBS_EMPLOYEE",
            "Employee, EMPLOYEE"
    })
    @WithMockUser
    void getEventByAuthorTest_ReturnsIsOk() throws Exception {
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        mockMvc.perform(get(LINK + "/author/{userId}", userId)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(eventService).findAllByAuthor(pageable, userId);
    }
}
