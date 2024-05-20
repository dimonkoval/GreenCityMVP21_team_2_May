package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.category.CategoryDtoResponse;
import greencity.entity.Category;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryDtoResponseMapperTest {

    @InjectMocks
    CategoryDtoResponseMapper mapper;

    @Test
    void convert() {
        Category categoryToBeConverted = ModelUtils.getCategory();
        CategoryDtoResponse expected = CategoryDtoResponse.builder()
                .id(categoryToBeConverted.getId())
                .name(categoryToBeConverted.getName())
                .build();

        assertEquals(expected, mapper.convert(categoryToBeConverted));
    }
}