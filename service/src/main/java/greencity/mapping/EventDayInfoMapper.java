package greencity.mapping;

import greencity.dto.econews.AddEcoNewsDtoRequest;
import greencity.dto.event.*;
import greencity.dto.event.model.EventDayInfo;
import greencity.dto.event.model.EventLocation;
import greencity.dto.event.model.EventLocationAddress;
import greencity.dto.event.model.EventLocationLink;
import greencity.dto.shoppinglistitem.CustomShoppingListItemResponseDto;
import greencity.entity.CustomShoppingListItem;
import greencity.entity.EcoNews;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.xml.stream.Location;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that used by {@link ModelMapper} to map {@link EventSaveDayInfoDto}
 * into {@link EventDayInfo}.
 * @author Viktoriia Herchanivska
 */
@Component
public class EventDayInfoMapper extends AbstractConverter<EventSaveDayInfoDto, EventDayInfo> {
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
                .isOnline(saveDayInfoDto.isOnline())
                .startTime(saveDayInfoDto.getStartTime())
                .endTime(saveDayInfoDto.getEndTime())
                .dayNumber(saveDayInfoDto.getDayNumber())
                .date(saveDayInfoDto.getDate())
                .location(getLocation(saveDayInfoDto.isOnline(), saveDayInfoDto.getLocation()))
                .build();
    }

    private EventLocation getLocation(boolean isOnline, String location) {
        if (isOnline) {
            return new EventLocationLink(location);
        }
        return new EventLocationAddress(location);
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
