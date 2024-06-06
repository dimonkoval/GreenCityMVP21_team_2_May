package greencity.mapping;

import greencity.constant.AppConstant;
import greencity.dto.event.EventResponseDto;
import greencity.dto.event.model.EventModelDto;
import greencity.dto.tag.TagTranslationVO;
import greencity.entity.localization.TagTranslation;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that used by {@link ModelMapper} to map {@link EventModelDto} into
 * {@link EventResponseDto}.
 */
@Component
public class EventResponseDtoMapper extends AbstractConverter<EventModelDto, EventResponseDto> {

    @Autowired
    private EventResponseDayInfoDtoMapper eventResponseDayInfoDtoMapper;

    @Autowired
    private EventImageDtoMapper eventImageDtoMapperMapper;

    @Autowired
    private EventAuthorDtoMapper eventAuthorDtoMapper;

    /**
     * Method for converting {@link EventModelDto} into {@link EventResponseDto}.
     *
     * @param eventModelDto object ot convert.
     * @return converted object.
     */
    @Override
    protected EventResponseDto convert(EventModelDto eventModelDto) {
        return EventResponseDto.builder()
                .id(eventModelDto.getId())
                .title(eventModelDto.getTitle())
                .dateTimes(eventModelDto.getDayInfos().stream()
                        .map(eventResponseDayInfoDtoMapper::convert)
                        .collect(Collectors.toList()))
                .description(eventModelDto.getDescription())
                .isOpen(eventModelDto.isOpen())
                .images(eventModelDto.getImages().stream()
                        .map(eventImageDtoMapperMapper::convert)
                        .collect(Collectors.toList()))
                .author(eventAuthorDtoMapper.convert(eventModelDto.getAuthor()))
                .tagsEn(eventModelDto.getTags().stream()
                        .flatMap(t -> t.getTagTranslations().stream())
                        .filter(t -> t.getLanguageVO().getCode().equals(AppConstant.DEFAULT_LANGUAGE_CODE))
                        .map(TagTranslationVO::getName).collect(Collectors.toList()))
                .tagsUa(eventModelDto.getTags().stream()
                        .flatMap(t -> t.getTagTranslations().stream())
                        .filter(t -> t.getLanguageVO().getCode().equals("ua"))
                        .map(TagTranslationVO::getName).collect(Collectors.toList()))
                .build();
    }

    /**
     * Method that build {@link List} of {@link EventResponseDto} from
     * {@link List} of {@link EventModelDto}.
     *
     * @param dtoList {@link List} of {@link EventModelDto}
     * @return {@link List} of {@link EventResponseDto}
     */
    public List<EventResponseDto> mapAllToList(List<EventModelDto> dtoList) {
        return dtoList.stream().map(this::convert).collect(Collectors.toList());
    }
}
