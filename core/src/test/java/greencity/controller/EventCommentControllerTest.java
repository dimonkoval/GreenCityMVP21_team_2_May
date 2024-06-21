package greencity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import greencity.converters.UserArgumentResolver;
import greencity.exception.handler.CustomExceptionHandler;
import greencity.service.EventCommentService;
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

import java.security.Principal;

import static greencity.ModelUtils.getPrincipal;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EventCommentControllerTest {

    private static final String eventsCommentLink = "/events/comments";

    private MockMvc mockMvc;

    @InjectMocks
    private EventCommentController eventCommentController;

    @Mock
    private EventCommentService eventCommentService;

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
                .standaloneSetup(eventCommentController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(),
                        new UserArgumentResolver(userService, modelMapper))
                .setControllerAdvice(new CustomExceptionHandler(errorAttributes, objectMapper))
                .build();
    }

    @Test
    void testUpdateCommentSuccess() throws Exception {
        Long commentId = 1L;
        String commentText = "This is a valid comment";

        doNothing().when(eventCommentService).update(commentId, commentText, principal.getName());

        mockMvc.perform(patch(eventsCommentLink + "/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentText)
                        .principal(principal))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateCommentInvalidText() throws Exception {
        Long commentId = 1L;
        String commentText = "";

        mockMvc.perform(patch(eventsCommentLink + "/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentText)
                        .principal(principal))
                .andExpect(status().isBadRequest());
    }
}
