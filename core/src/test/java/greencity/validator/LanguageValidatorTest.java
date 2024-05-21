package greencity.validator;

import greencity.service.LanguageService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.powermock.api.mockito.PowerMockito.when;

@ExtendWith(MockitoExtension.class)
class LanguageValidatorTest {
    @Mock
    private LanguageService languageService;

    @Mock
    ConstraintValidatorContext context;

    @InjectMocks
    private LanguageValidator languageValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsValid_validLocale_shouldBeTrue() {
        // Given
        List<String> mockLanguageCodes = Arrays.asList("en", "ua");
        when(languageService.findAllLanguageCodes()).thenReturn(mockLanguageCodes);
        languageValidator.initialize(null);
        // When
        Locale validLocale = Locale.of("en");
        // Then
        assertTrue(languageValidator.isValid(validLocale, context));
    }

    @Test
    void testIsValid_invalidLocale_shouldBeFalse() {
        // Given
        List<String> mockLanguageCodes = Arrays.asList("en", "ua");
        when(languageService.findAllLanguageCodes()).thenReturn(mockLanguageCodes);
        languageValidator.initialize(null);
        // When
        Locale invalidLocale = Locale.of("abcdefg");
        // Then
        assertFalse(languageValidator.isValid(invalidLocale, context));
    }

    @Test
    void testIsValid_emptyLocale_shouldBeFalse() {
        // Given
        List<String> mockLanguageCodes = Arrays.asList("en", "ua");
        when(languageService.findAllLanguageCodes()).thenReturn(mockLanguageCodes);
        languageValidator.initialize(null);
        // When
        Locale invalidLocale = Locale.of("");
        // Then
        assertFalse(languageValidator.isValid(invalidLocale, context));
    }
}