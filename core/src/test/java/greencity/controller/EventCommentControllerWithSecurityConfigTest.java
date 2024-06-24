package greencity.controller;

import greencity.ModelUtils;
import greencity.config.PageableConfig;
import greencity.config.SecurityConfig;
import greencity.security.jwt.JwtTool;
import greencity.service.EventCommentService;
import greencity.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SecurityConfig.class, EventCommentController.class, PageableConfig.class})
@WebAppConfiguration
@EnableWebMvc
@Import({JwtTool.class})
@TestPropertySource(properties = {
        "accessTokenValidTimeInMinutes=60",
        "refreshTokenValidTimeInMinutes=1440",
        "tokenKey=secretTokenKey"
})
class EventCommentControllerWithSecurityConfigTest {

    private static final String eventsCommentLink = "/events/comments";

    private MockMvc mockMvc;

    @MockBean
    private EventCommentService eventCommentService;

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
    void testUpdateComment_ReturnsIsUnauthorized() throws Exception {
        Long commentId = 1L;
        String commentText = "This is a test comment";

        mockMvc.perform(patch(eventsCommentLink + "/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentText))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @CsvSource({
            "User, USER",
            "Moderator, MODERATOR",
            "Ubs_Employee, UBS_EMPLOYEE",
            "Employee, EMPLOYEE"
    })
    @WithMockUser
    void testUpdateWithUsers_ReturnsIsNoContent() throws Exception {
        Long commentId = 1L;
        String commentText = "This is a test comment";

        mockMvc.perform(patch(eventsCommentLink + "/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentText))
                .andExpect(status().isNoContent());
    }

}
