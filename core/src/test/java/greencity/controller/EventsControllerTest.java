package greencity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import greencity.converters.UserArgumentResolver;
import greencity.dto.event.EventRequestSaveDto;
import greencity.exception.exceptions.BadRequestException;
import greencity.exception.exceptions.NotFoundException;
import greencity.exception.handler.CustomExceptionHandler;
import greencity.service.EventService;
import greencity.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.security.Principal;

import static greencity.ModelUtils.getPrincipal;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EventsControllerTest {

    private static final String eventsLink = "/events";

    private MockMvc mockMvc;

    @InjectMocks
    private EventsController eventsController;

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ObjectMapper objectMapper;

    private Principal principal = getPrincipal();

    private ErrorAttributes errorAttributes = new DefaultErrorAttributes();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(eventsController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(),
                        new UserArgumentResolver(userService, modelMapper))
                .setControllerAdvice(new CustomExceptionHandler(errorAttributes, objectMapper))
                .build();
    }

    @Test
    void saveTest_ReturnsIsCreated() throws Exception {
        Principal principal = Mockito.mock(Principal.class);
        when(principal.getName()).thenReturn("testUser@gmail.com");

        String json = "{\n" +
                "  \"title\": \"string\",\n" +
                "  \"daysInfo\": [\n" +
                "    {\n" +
                "      \"startDateTime\": \"2024-06-02T14:20:45.252Z\",\n" +
                "      \"endDateTime\": \"2024-06-02T14:20:45.252Z\",\n" +
                "      \"dayNumber\": 0,\n" +
                "      \"allDay\": true,\n" +
                "      \"status\": \"ONLINE\",\n" +
                "      \"link\": \"string\",\n" +
                "      \"address\": null\n" +
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

        mockMvc.perform(multipart(eventsLink)
                        .file(jsonFile)
                        .file(imageFile)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .principal(principal))
                .andExpect(status().isCreated());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        EventRequestSaveDto eventRequestSaveDto = mapper.readValue(json, EventRequestSaveDto.class);

        verify(eventService).save(eq(eventRequestSaveDto), any(MultipartFile[].class), eq(null));
    }

    @Test
    void saveTest_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post(eventsLink)
                        .content("{}")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTest_ReturnsUnsupportedMediaType() throws Exception {
        mockMvc.perform(post(eventsLink)
                        .content("{}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void findAllEventsTest_ReturnsIsOk() throws Exception {
        int pageNumber = 1;
        int pageSize = 20;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        mockMvc.perform(get(eventsLink + "?page=1"))
                .andExpect(status().isOk());

        verify(eventService).findAll(pageable);
    }

    @Test
    void findAllEventsTest_ReturnsBadRequest() throws Exception {
        Pageable pageable = PageRequest.of(0, 1);

        when(eventService.findAll(pageable)).thenThrow(BadRequestException.class);

        mockMvc.perform(get(eventsLink)
                        .param("page", "0")
                        .param("size", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getEventByIdTest_ReturnsIsOk() throws Exception {
        mockMvc.perform(get(eventsLink + "/{id}", 1L))
                .andExpect(status().isOk());

        verify(eventService).findById(1L);
    }

    @Test
    void getEventByIdTest_ReturnsNotFound() throws Exception {
        when(eventService.findById(500L)).thenThrow(NotFoundException.class);

        mockMvc.perform(get(eventsLink + "/{id}", 500L))
                .andExpect(status().isNotFound());

        verify(eventService).findById(500L);
    }

    @Test
    void getEventByAuthorTest_ReturnsIsOk() throws Exception {
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        mockMvc.perform(get(eventsLink + "/author/{userId}", userId)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(eventService, times(1)).findAllByAuthor(pageable, userId);
    }

    @Test
    void getEventByAuthorTest_ReturnsBadRequest() throws Exception {
        Long userId = -1L;
        Pageable pageable = PageRequest.of(0, 1);

        when(eventService.findAllByAuthor(pageable, userId)).thenThrow(BadRequestException.class);

        mockMvc.perform(get(eventsLink + "/author/{userId}", userId)
                        .param("page", "0")
                        .param("size", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getEventByAuthorTest_ReturnsNotFound() throws Exception {
        Long userId = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        when(eventService.findAllByAuthor(pageable, userId)).thenThrow(NotFoundException.class);

        mockMvc.perform(get(eventsLink + "/author/{userId}", userId)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTest_ReturnsOk() throws Exception {
        Principal principal = Mockito.mock(Principal.class);

        when(principal.getName()).thenReturn("testUser@gmail.com");

        mockMvc.perform(delete(eventsLink + "/delete/{eventId}", 1L)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(eventService).delete(1L, principal.getName());
    }

    @Test
    void deleteTest_WhenEventDoesNotExist() throws Exception {
        Principal principal = Mockito.mock(Principal.class);

        when(principal.getName()).thenReturn("testUser@gmail.com");
        doThrow(new NotFoundException("Event not found")).when(eventService).delete(anyLong(), anyString());

        mockMvc.perform(delete(eventsLink + "/delete/{eventId}", 1L)
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertInstanceOf(
                        NotFoundException.class,
                        result.getResolvedException()));

        verify(eventService).delete(1L, principal.getName());
    }

    @Test
    void deleteTest_ReturnsBadRequest_WhenInvalidIdIsPassed() throws Exception {
        Principal principal = Mockito.mock(Principal.class);

        when(principal.getName()).thenReturn("testUser@gmail.com");

        mockMvc.perform(delete(eventsLink + "/delete/{eventId}", "not_number")
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertInstanceOf(
                        MethodArgumentTypeMismatchException.class,
                        result.getResolvedException()));

        verify(eventService, never()).delete(anyLong(), anyString());
    }
}
