package greencity.mapping;

import greencity.dto.event.model.EventModelDto;
import greencity.dto.event.EventRequestSaveDto;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link ModelMapper} to map {@link EventRequestSaveDto}
 * into {@link EventModelDto}.
 * @author Viktoriia Herchanivska
 */
@Component
public class EventModelDtoMapper extends AbstractConverter<EventRequestSaveDto, EventModelDto> {
    private EventDayInfoMapper eventDayInfoMapper = new EventDayInfoMapper();

    /**
     * Method for converting {@link EventRequestSaveDto} into {@link EventModelDto}.
     *
     * @param eventRequestSaveDto object to convert.
     * @return converted object.
     * @author Viktoriia Herchanivska
     */
    @Override
    protected EventModelDto convert(EventRequestSaveDto eventRequestSaveDto) {
        return EventModelDto.builder()
                .title(eventRequestSaveDto.getTitle())
                .dayInfos(eventDayInfoMapper.mapAllToList(eventRequestSaveDto.getDaysInfo()))
                .description(eventRequestSaveDto.getDescription())
                .isOpen(eventRequestSaveDto.isOpen())
                .build();
    }
}
