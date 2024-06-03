package greencity.mapping;

import greencity.dto.event.EventAddressDto;
import greencity.dto.event.model.EventAddress;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link ModelMapper} to map {@link EventAddressDto}
 * into {@link EventAddress}.
 * @author Viktoriia Herchanivska
 */
@Component
public class EventAddressMapper extends AbstractConverter<EventAddressDto, EventAddress> {
    /**
     * Method for converting {@link EventAddressDto} into {@link EventAddress}.
     *
     * @param eventAddressDto object to convert.
     * @return converted object.
     * @author Viktoriia Herchanivska
     */
    @Override
    protected EventAddress convert(EventAddressDto eventAddressDto) {
        if (eventAddressDto == null) return null;
        return  EventAddress.builder()
                .latitude(eventAddressDto.getLatitude())
                .longitude(eventAddressDto.getLongitude())
                .addressEn(eventAddressDto.getAddressEn())
                .addressUa(eventAddressDto.getAddressUa())
                .build();
    }
}
