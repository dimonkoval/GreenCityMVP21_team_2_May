package greencity.mapping;

import greencity.dto.event.*;
import greencity.entity.event.EventDayInfo;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that used by {@link ModelMapper} to map {@link EventSaveDayInfoDto}
 * into {@link EventDayInfo}.
 * @author Viktoriia Herchanivska
 */
@Component
public class EventDayInfoMapper extends AbstractConverter<EventSaveDayInfoDto, EventDayInfo> {
    private EventAddressMapper mapper = new EventAddressMapper();
    /**
     * Method for converting {@link EventSaveDayInfoDto} into {@link EventDayInfo}.
     *
     * @param saveDayInfoDto object to convert.
     * @return converted object.
     * @author Viktoriia Herchanivska
     */
    @Override
    protected EventDayInfo convert(EventSaveDayInfoDto saveDayInfoDto) {
        return EventDayInfo.builder()
                .isAllDay(saveDayInfoDto.isAllDay())
                .startDateTime(saveDayInfoDto.getStartDateTime())
                .endDateTime(saveDayInfoDto.getEndDateTime())
                .dayNumber(saveDayInfoDto.getDayNumber())
                .link(saveDayInfoDto.getLink())
                .address(mapper.convert(saveDayInfoDto.getAddress()))
                .build();
    }

    /**
     * Method that build {@link List} of {@link EventDayInfo} from
     * {@link List} of {@link EventSaveDayInfoDto}.
     *
     * @param dtoList {@link List} of {@link EventSaveDayInfoDto}
     * @return {@link List} of {@link EventDayInfo}
     * @author Viktoriia Herchanivska
     */
    public List<EventDayInfo> mapAllToList(
            List<EventSaveDayInfoDto> dtoList) {
        return dtoList.stream().map(this::convert).collect(Collectors.toList());
    }
}
