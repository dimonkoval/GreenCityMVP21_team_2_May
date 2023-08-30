package greencity.validator;

import greencity.dto.econews.AddEcoNewsDtoRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static greencity.ModelUtils.getAddEcoNewsDtoRequest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class EcoNewsDtoRequestValidatorTest {
    @InjectMocks
    private EcoNewsDtoRequestValidator validator;

    @Test
    void isValidTrueTest() {
        AddEcoNewsDtoRequest request = getAddEcoNewsDtoRequest();
        request.setSource("https://eco-lavca.ua/");
        assertTrue(validator.isValid(request, null));
    }
}
