package greencity.mapping;

import greencity.entity.event.Event;
import greencity.dto.event.EventRequestSaveDto;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link ModelMapper} to map {@link EventRequestSaveDto}
 * into {@link Event}.
 * @author Viktoriia Herchanivska
 */
@Component
public class EventModelDtoMapper extends AbstractConverter<EventRequestSaveDto, Event> {
    private EventDayInfoMapper eventDayInfoMapper = new EventDayInfoMapper();

    /**
     * Method for converting {@link EventRequestSaveDto} into {@link Event}.
     *
     * @param eventRequestSaveDto object to convert.
     * @return converted object.
     * @author Viktoriia Herchanivska
     */
    @Override
    protected Event convert(EventRequestSaveDto eventRequestSaveDto) {
        return Event.builder()
                .title(eventRequestSaveDto.getTitle())
                .dayInfos(eventDayInfoMapper.mapAllToList(eventRequestSaveDto.getDaysInfo()))
                .description(eventRequestSaveDto.getDescription())
                .isOpen(eventRequestSaveDto.isOpen())
                .build();
    }
}
