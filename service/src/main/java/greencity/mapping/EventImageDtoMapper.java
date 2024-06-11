package greencity.mapping;

import greencity.dto.event.EventImageDto;
import greencity.entity.event.EventImage;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link ModelMapper} to map {@link EventImage} into
 * {@link EventImageDto}.
 */
@Component
public class EventImageDtoMapper extends AbstractConverter<EventImage, EventImageDto> {
    /**
     * Method for converting {@link EventImage} into {@link EventImageDto}.
     *
     * @param eventImage object ot convert.
     * @return converted object.
     */
    @Override
    protected EventImageDto convert(EventImage eventImage) {
        return EventImageDto.builder()
                .imagePath(eventImage.getImagePath())
                .isMain(eventImage.isMain())
                .build();
    }
}
