package greencity.validator;

import greencity.constant.ErrorMessage;
import greencity.dto.econews.AddEcoNewsDtoRequest;
import greencity.exception.exceptions.WrongCountOfTagsException;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static greencity.ModelUtils.getAddEcoNewsDtoRequest;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EcoNewsDtoRequestValidatorTest {
    @InjectMocks
    private EcoNewsDtoRequestValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void isValidTrueTest() {
        AddEcoNewsDtoRequest request = getAddEcoNewsDtoRequest();
        request.setSource("https://eco-lavca.ua/");
        assertTrue(validator.isValid(request, null));
    }

    @Test
    void testValidRequestWithValidSource() {
        AddEcoNewsDtoRequest request = getAddEcoNewsDtoRequest();
        request.setSource("https://eco-lavca.ua/");
        request.setTags(Arrays.asList("tag1", "tag2", "tag3"));

        assertTrue(validator.isValid(request, context));
    }

    @Test
    void testValidRequestWithNoSource() {
        AddEcoNewsDtoRequest request = getAddEcoNewsDtoRequest();
        request.setSource(null);
        request.setTags(Arrays.asList("tag1", "tag2", "tag3"));

        assertTrue(validator.isValid(request, context));
    }

    @Test
    void testValidRequestWithTooManyTags() {
        AddEcoNewsDtoRequest request = getAddEcoNewsDtoRequest();
        request.setSource("https://eco-lavca.ua/");
        request.setTags(Arrays.asList("tag1", "tag2", "tag3", "tag4"));

        Exception exception = assertThrows(WrongCountOfTagsException.class, () -> validator.isValid(request, context));

        assertEquals(ErrorMessage.WRONG_COUNT_OF_TAGS_EXCEPTION, exception.getMessage());
    }

    @Test
    void testValidRequestWithNoSourceAndTooManyTags() {
        AddEcoNewsDtoRequest request = getAddEcoNewsDtoRequest();
        request.setSource(null);
        request.setSource("https://eco-lavca.ua/");
        request.setTags(Arrays.asList("tag1", "tag2", "tag3", "tag4"));

        Exception exception = assertThrows(WrongCountOfTagsException.class, () -> validator.isValid(request, context));

        assertEquals(ErrorMessage.WRONG_COUNT_OF_TAGS_EXCEPTION, exception.getMessage());
    }

    @Test
    void testValidRequestWithNoTags() {
        AddEcoNewsDtoRequest request = getAddEcoNewsDtoRequest();
        request.setSource("https://eco-lavca.ua/");
        request.setTags(Collections.emptyList());

        Exception exception = assertThrows(WrongCountOfTagsException.class, () -> validator.isValid(request, context));

        assertEquals(ErrorMessage.WRONG_COUNT_OF_TAGS_EXCEPTION, exception.getMessage());
    }

    @Test
    void testValidRequestWithNoSourceAndNoTags() {
        AddEcoNewsDtoRequest request = getAddEcoNewsDtoRequest();
        request.setSource(null);
        request.setTags(Collections.emptyList());

        Exception exception = assertThrows(WrongCountOfTagsException.class, () -> validator.isValid(request, context));

        assertEquals(ErrorMessage.WRONG_COUNT_OF_TAGS_EXCEPTION, exception.getMessage());
    }

}
