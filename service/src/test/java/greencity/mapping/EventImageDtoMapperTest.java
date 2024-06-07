package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.event.EventImageDto;
import greencity.dto.event.model.EventImage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class EventImageDtoMapperTest {

    @InjectMocks
    private EventImageDtoMapper mapper;

    @Test
    void convert() {
        EventImage eventImage = ModelUtils.getEventImage();

        EventImageDto expected = EventImageDto.builder()
                .imagePath(eventImage.getImagePath())
                .isMain(eventImage.isMain())
                .build();

        assertEquals(expected, mapper.convert(eventImage));
    }

}
