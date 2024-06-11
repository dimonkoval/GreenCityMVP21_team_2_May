package greencity.mapping;

import greencity.constant.EmailNotificationMessagesConstants;
import greencity.entity.event.EventDayInfo;
import greencity.entity.event.Event;
import greencity.enums.EventStatus;
import greencity.message.EventEmailMessage;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link ModelMapper} to map {@link Event} into
 * {@link EventEmailMessage}.
 */
@Component
public class EventEmailMessageMapper extends AbstractConverter<Event, EventEmailMessage> {

    @Value("${greencitymvp.server.address}")
    private String greenCityMvpServerAddress;

    /**
     * Method for converting {@link Event} into {@link EventEmailMessage}.
     *
     * @param event object ot convert.
     * @return converted object.
     */
    @Override
    protected EventEmailMessage convert(Event event) {
        return EventEmailMessage.builder()
                .email(event.getAuthor().getEmail())
                .subject(EmailNotificationMessagesConstants.EVENT_CREATION_SUBJECT)
                .author(event.getAuthor().getName())
                .eventTitle(event.getTitle())
                .description(event.getDescription())
                .isOpen(event.isOpen())
                .status(setStatusFromEntity(event.getDayInfos().getFirst()))
                .link(event.getDayInfos().getFirst().getLink())
                .startDateTime(event.getDayInfos().getFirst().getStartDateTime())
                .endDateTime(event.getDayInfos().getFirst().getEndDateTime())
                .address(event.getDayInfos().getFirst().getAddress())
                .linkToEvent(greenCityMvpServerAddress + "/events/" + event.getId())
                .build();
    }

    private EventStatus setStatusFromEntity(EventDayInfo eventDayInfo) {
        boolean isOnline = eventDayInfo.getLink() != null &&
                !eventDayInfo.getLink().isBlank();
        boolean isOffline = eventDayInfo.getAddress() != null &&
                eventDayInfo.getAddress().getLatitude() != null &&
                eventDayInfo.getAddress().getLongitude() != null;
        if (isOnline) {
            if (isOffline) {
                return EventStatus.ONLINE_OFFLINE;
            }
            return EventStatus.ONLINE;
        }
        return EventStatus.OFFLINE;
    }
}
