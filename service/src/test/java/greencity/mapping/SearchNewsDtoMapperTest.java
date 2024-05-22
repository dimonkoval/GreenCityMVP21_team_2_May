package greencity.mapping;

import greencity.dto.search.SearchNewsDto;
import greencity.entity.EcoNews;
import greencity.entity.Language;
import greencity.entity.Tag;
import greencity.entity.User;
import greencity.entity.localization.TagTranslation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SearchNewsDtoMapperTest {
    @InjectMocks
    SearchNewsDtoMapper mapper;

    @BeforeEach
    void setUp() {
        LocaleContextHolder.setLocale(Locale.ENGLISH);
    }

    @Test
    void convertTest() {
        EcoNews expected = new EcoNews();
        expected.setId(1L);
        expected.setTitle("Test Title");
        expected.setCreationDate(ZonedDateTime.now());

        User author = new User();
        author.setId(1L);
        author.setName("Test Author");
        expected.setAuthor(author);

        Tag tag = new Tag();
        tag.setId(1L);

        List<TagTranslation> tagTranslations = new ArrayList<>();
        TagTranslation tagTranslation = new TagTranslation();
        tagTranslation.setName("Test Tag");
        Language language = new Language();
        language.setCode("en");
        tagTranslation.setLanguage(language);
        tagTranslations.add(tagTranslation);
        tag.setTagTranslations(tagTranslations);
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        expected.setTags(tags);

        SearchNewsDto actual = mapper.convert(expected);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getAuthor().getId(), actual.getAuthor().getId());
        assertEquals(expected.getAuthor().getName(), actual.getAuthor().getName());
        assertEquals(expected.getTags().size(), actual.getTags().size());
        assertEquals(expected.getTags().getFirst().getTagTranslations().getFirst().getName(),
                actual.getTags().getFirst());
    }
}