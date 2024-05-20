package greencity.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ImageValidatorTest {

    @InjectMocks
    ImageValidator imageValidator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    void isValid_WithValidImageJPEG_True() {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[1024]);
        assertTrue(imageValidator.isValid(image, constraintValidatorContext));
    }

    @Test
    void isValid_WithValidImageJPG_True() {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpg", new byte[10 * 1024 * 1024]); //max size
        assertTrue(imageValidator.isValid(image, constraintValidatorContext));
    }

    @Test
    void isValid_WithValidImagePNG_True() {
        MockMultipartFile image = new MockMultipartFile("image", "image.png", "image/png", new byte[1]);
        assertTrue(imageValidator.isValid(image, constraintValidatorContext));
    }

    @Test
    void isValid_WithEmptyImage_True() {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[0]);
        assertTrue(imageValidator.isValid(image, constraintValidatorContext));
    }

    @Test
    void isValid_WithNullImage_True() {
        assertTrue(imageValidator.isValid(null, constraintValidatorContext));
    }

    @Test
    void isValid_WithInvalidContentType_False() {
        MockMultipartFile image = new MockMultipartFile("image", "image.txt", "text/plain", new byte[0]);
        assertFalse(imageValidator.isValid(image, constraintValidatorContext));
    }

    @Test
    void isValid_WithInvalidImageSize_False() {
        int invalidSize = (10 * 1024 * 1024) + 1;
        MockMultipartFile image = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[invalidSize]);
        assertFalse(imageValidator.isValid(image, constraintValidatorContext));
    }
}