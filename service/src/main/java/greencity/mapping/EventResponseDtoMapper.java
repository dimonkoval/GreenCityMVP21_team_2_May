package greencity.mapping;

import greencity.constant.AppConstant;
import greencity.dto.event.EventResponseDto;
import greencity.entity.event.Event;
import greencity.dto.tag.TagTranslationVO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that used by {@link ModelMapper} to map {@link Event} into
 * {@link EventResponseDto}.
 */
@Component
public class EventResponseDtoMapper extends AbstractConverter<Event, EventResponseDto> {

    @Autowired
    private EventResponseDayInfoDtoMapper eventResponseDayInfoDtoMapper;

    @Autowired
    private EventImageDtoMapper eventImageDtoMapperMapper;

    @Autowired
    private EventAuthorDtoMapper eventAuthorDtoMapper;

    /**
     * Method for converting {@link Event} into {@link EventResponseDto}.
     *
     * @param event object ot convert.
     * @return converted object.
     */
    @Override
    protected EventResponseDto convert(Event event) {
        return EventResponseDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .dateTimes(event.getDayInfos().stream()
                        .map(eventResponseDayInfoDtoMapper::convert)
                        .collect(Collectors.toList()))
                .description(event.getDescription())
                .isOpen(event.isOpen())
                .images(event.getImages().stream()
                        .map(eventImageDtoMapperMapper::convert)
                        .collect(Collectors.toList()))
                .author(eventAuthorDtoMapper.convert(event.getAuthor()))
                .tagsEn(event.getTags() == null || event.getTags().size() == 0 ?
                        null :
                        event.getTags().stream()
                        .flatMap(t -> t.getTagTranslations().stream())
                        .filter(t -> t.getLanguageVO().getCode().equals(AppConstant.DEFAULT_LANGUAGE_CODE))
                        .map(TagTranslationVO::getName).collect(Collectors.toList()))
                .tagsUa(event.getTags() == null || event.getTags().size() == 0 ?
                        null :
                        event.getTags().stream()
                        .flatMap(t -> t.getTagTranslations().stream())
                        .filter(t -> t.getLanguageVO().getCode().equals("ua"))
                        .map(TagTranslationVO::getName).collect(Collectors.toList()))
                .build();
    }

    /**
     * Method that build {@link List} of {@link EventResponseDto} from
     * {@link List} of {@link Event}.
     *
     * @param dtoList {@link List} of {@link Event}
     * @return {@link List} of {@link EventResponseDto}
     */
    public List<EventResponseDto> mapAllToList(List<Event> dtoList) {
        return dtoList.stream().map(this::convert).collect(Collectors.toList());
    }
}
