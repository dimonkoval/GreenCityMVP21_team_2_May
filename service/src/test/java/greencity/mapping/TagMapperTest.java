package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.tag.TagVO;
import greencity.entity.Language;
import greencity.entity.Tag;
import greencity.entity.localization.TagTranslation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TagMapperTest {

    @InjectMocks
    TagMapper mapper;

    @Test
    void convert() {
        TagVO expected = ModelUtils.getTagVO();

        List<TagTranslation> tagTranslations = new ArrayList<>();
        expected.getTagTranslations()
                .forEach(tt -> tagTranslations.add(TagTranslation.builder().id(tt.getId()).name(tt.getName())
                        .language(Language.builder().code(tt.getLanguageVO().getCode()).id(tt.getLanguageVO().getId()).build())
                        .build()));

        Tag tag = Tag.builder()
                .id(expected.getId())
                .type(expected.getType())
                .tagTranslations(tagTranslations)
                .build();

        assertEquals(tag, mapper.convert(expected));
    }
}
