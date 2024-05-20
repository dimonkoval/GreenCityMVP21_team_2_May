package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.econews.EcoNewsDto;
import greencity.entity.EcoNews;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class EcoNewsDtoMapperTest {

    @InjectMocks
    private EcoNewsDtoMapper mapper;

    @Test
    void convert() {
        EcoNews ecoNews = ModelUtils.getEcoNews();
        EcoNewsDto result = mapper.convert(ecoNews);

        assertNotNull(result);
        assertEquals(ecoNews.getId(), result.getId());
        assertNotNull(result.getAuthor());
        assertEquals(ecoNews.getAuthor().getId(), result.getAuthor().getId());
        assertEquals(ecoNews.getAuthor().getName(), result.getAuthor().getName());
        assertEquals(ecoNews.getTitle(), result.getTitle());
        assertEquals(ecoNews.getText(), result.getContent());
        assertEquals(ecoNews.getCreationDate(), result.getCreationDate());
        assertEquals(ecoNews.getImagePath(), result.getImagePath());
        assertEquals(ecoNews.getShortInfo(), result.getShortInfo());
    }

}
