package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.user.EcoNewsAuthorDto;
import greencity.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EcoNewsAuthorDtoMapperTest {
    @InjectMocks
    EcoNewsAuthorDtoMapper mapper;

    @Test
    void convert() {
        User userToBeConverted = ModelUtils.getUser();
        EcoNewsAuthorDto expected = EcoNewsAuthorDto.builder()
                .id(userToBeConverted.getId())
                .name(userToBeConverted.getName())
                .build();

        assertEquals(expected, mapper.convert(userToBeConverted));
    }
}