package greencity.dto.event;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;

class EventRequestSaveDtoTest {

    private static EventRequestSaveDto eventRequestSaveDto;
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeEach
    void setUp() {
        eventRequestSaveDto = new EventRequestSaveDto();
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

}
