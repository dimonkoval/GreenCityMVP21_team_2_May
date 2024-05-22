package greencity.mapping;

import greencity.entity.Language;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import greencity.dto.tag.NewTagDto;
import greencity.entity.Tag;
import greencity.entity.localization.TagTranslation;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewTagDtoMapperTest {
    @InjectMocks
    NewTagDtoMapper mapper;
    @Mock
    TagTranslation tagTranslationEn;
    @Mock
    TagTranslation tagTranslationUa;

    @Test
    void convertTest() {
        Tag tag = new Tag();
        tag.setId(1L);

        when(tagTranslationEn.getName()).thenReturn("Test content");
        Language languageEn = new Language();
        languageEn.setId(1L);
        languageEn.setCode("en");

        when(tagTranslationEn.getLanguage()).thenReturn(languageEn);

        when(tagTranslationUa.getName()).thenReturn("Тестовий вміст");
        Language languageUa = new Language();
        languageUa.setId(2L);
        languageUa.setCode("ua");
        when(tagTranslationUa.getLanguage()).thenReturn(languageUa);

        List<TagTranslation> tagTranslations = List.of(tagTranslationEn, tagTranslationUa);

        tag.setTagTranslations(tagTranslations);

        NewTagDto resultDto = mapper.convert(tag);

        assertEquals("Test content", resultDto.getName());
        assertEquals("Тестовий вміст", resultDto.getNameUa());
        assertEquals(1L, resultDto.getId());
    }
}