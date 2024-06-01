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
                .isOnline(eventDayInfo.isOnline())
                .startDateTime(eventDayInfo.getStartDateTime())
                .endDateTime(eventDayInfo.getEndDateTime())
                .dayNumber(eventDayInfo.getDayNumber())
                .location(eventDayInfo.getLocation().toString())
                .build();
    }
}
