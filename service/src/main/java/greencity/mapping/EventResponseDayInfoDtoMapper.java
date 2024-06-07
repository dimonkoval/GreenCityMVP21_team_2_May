package greencity.mapping;

import greencity.dto.event.EventResponseDayInfoDto;
import greencity.dto.event.model.EventDayInfo;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link ModelMapper} to map {@link EventDayInfo} into
 * {@link EventResponseDayInfoDto}.
 */
@Component
public class EventResponseDayInfoDtoMapper extends AbstractConverter<EventDayInfo, EventResponseDayInfoDto> {
    EventAddressDtoMapper mapper = new EventAddressDtoMapper();
    /**
     * Method for converting {@link EventDayInfo} into {@link EventResponseDayInfoDto}.
     *
     * @param eventDayInfo object ot convert.
     * @return converted object.
     */
    @Override
    protected EventResponseDayInfoDto convert(EventDayInfo eventDayInfo) {
        return EventResponseDayInfoDto.builder()
                .isAllDay(eventDayInfo.isAllDay())
                .startDateTime(eventDayInfo.getStartDateTime())
                .endDateTime(eventDayInfo.getEndDateTime())
                .dayNumber(eventDayInfo.getDayNumber())
                .status(eventDayInfo.getStatus())
                .link(eventDayInfo.getLink())
                .address(mapper.convert(eventDayInfo.getAddress()))
                .build();
    }
}
