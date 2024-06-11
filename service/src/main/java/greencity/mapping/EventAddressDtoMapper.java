package greencity.mapping;

import greencity.dto.event.EventAddressDto;
import greencity.entity.event.EventAddress;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link ModelMapper} to map {@link EventAddress}
 * into {@link EventAddressDto}.
 * @author Viktoriia Herchanivska
 */
@Component
public class EventAddressDtoMapper extends AbstractConverter<EventAddress, EventAddressDto> {
    /**
     * Method for converting {@link EventAddress} into {@link EventAddressDto}.
     *
     * @param eventAddress object to convert.
     * @return converted object.
     * @author Viktoriia Herchanivska
     */
    @Override
    protected EventAddressDto convert(EventAddress eventAddress) {
        if (eventAddress == null) return null;
        return  EventAddressDto.builder()
                .latitude(eventAddress.getLatitude())
                .longitude(eventAddress.getLongitude())
                .addressEn(eventAddress.getAddressEn())
                .addressUa(eventAddress.getAddressUa())
                .build();
    }
}
