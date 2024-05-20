package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.category.CategoryDto;
import greencity.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryDtoMapperTest {

    @InjectMocks
    CategoryDtoMapper mapper;

    @Test
    void convert() {
        Category expected = ModelUtils.getCategory();
        expected.setId(null);

        CategoryDto categoryToBeConverted = CategoryDto.builder()
                .name(expected.getName())
                .build();
        assertEquals(expected, mapper.convert(categoryToBeConverted));
    }
}