package greencity.mapping;

import greencity.constant.EmailNotificationMessagesConstants;
import greencity.dto.event.model.EventModelDto;
import greencity.message.EventEmailMessage;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link ModelMapper} to map {@link EventModelDto} into
 * {@link EventEmailMessage}.
 */
@Component
public class EventEmailMessageMapper extends AbstractConverter<EventModelDto, EventEmailMessage> {

    @Value("${greencitymvp.server.address}")
    private String greenCityMvpServerAddress;

    /**
     * Method for converting {@link EventModelDto} into {@link EventEmailMessage}.
     *
     * @param eventModelDto object ot convert.
     * @return converted object.
     */
    @Override
    protected EventEmailMessage convert(EventModelDto eventModelDto) {
        return EventEmailMessage.builder()
                .email(eventModelDto.getAuthor().getEmail())
                .subject(EmailNotificationMessagesConstants.EVENT_CREATION_SUBJECT)
                .author(eventModelDto.getAuthor().getName())
                .eventTitle(eventModelDto.getTitle())
                .description(eventModelDto.getDescription())
                .isOpen(eventModelDto.isOpen())
                .status(eventModelDto.getDayInfos().getFirst().getStatus())
                .link(eventModelDto.getDayInfos().getFirst().getLink())
                .startDateTime(eventModelDto.getDayInfos().getFirst().getStartDateTime())
                .endDateTime(eventModelDto.getDayInfos().getFirst().getEndDateTime())
                .address(eventModelDto.getDayInfos().getFirst().getAddress())
                .linkToEvent(greenCityMvpServerAddress + "/events/" + eventModelDto.getId())
                .build();
    }
}
