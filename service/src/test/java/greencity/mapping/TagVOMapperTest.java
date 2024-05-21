package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.language.LanguageVO;
import greencity.dto.tag.TagTranslationVO;
import greencity.dto.tag.TagVO;
import greencity.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TagVOMapperTest {
    @InjectMocks
    TagVOMapper mapper;

    @Test
    void convert() {
        Tag tag = ModelUtils.getTag();

        TagVO tagToBeConverted = TagVO.builder()
                .id(tag.getId())
                .type(tag.getType())
                .tagTranslations(tag.getTagTranslations().stream()
                        .map(tagTranslation -> TagTranslationVO.builder()
                                .id(tagTranslation.getId())
                                .name(tagTranslation.getName())
                                .languageVO(LanguageVO.builder()
                                        .id(tagTranslation.getLanguage().getId())
                                        .code(tagTranslation.getLanguage().getCode())
                                        .build())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        assertEquals(tagToBeConverted, mapper.convert(tag));
    }
}
