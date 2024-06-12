package greencity.mapping;

import greencity.dto.language.LanguageTranslationDTO;
import greencity.entity.HabitFactTranslation;
import greencity.entity.Language;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LanguageTranslationDtoMapperTest {
    @InjectMocks
    LanguageTranslationDtoMapper mapper;

    @Test
    void convertTest() {
        HabitFactTranslation expected = new HabitFactTranslation();
        expected.setContent("Test content");
        Language language = new Language();
        language.setId(1L);
        language.setCode("en");
        expected.setLanguage(language);

        LanguageTranslationDTO actual = mapper.convert(expected);

        assertEquals(expected.getContent(), actual.getContent());
        assertEquals(expected.getLanguage().getId(), actual.getLanguage().getId());
        assertEquals(expected.getLanguage().getCode(), actual.getLanguage().getCode());
    }
}