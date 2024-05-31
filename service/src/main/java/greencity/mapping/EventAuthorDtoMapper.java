package greencity.mapping;

import greencity.dto.event.EventAuthorDto;
import greencity.dto.user.UserVO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Class that used by {@link ModelMapper} to map {@link UserVO} into
 * {@link EventAuthorDto}.
 */
@Component
public class EventAuthorDtoMapper extends AbstractConverter<UserVO, EventAuthorDto> {
    /**
     * Method for converting {@link UserVO} into {@link EventAuthorDto}.
     *
     * @param userVO object ot convert.
     * @return converted object.
     */
    @Override
    protected EventAuthorDto convert(UserVO userVO) {
        return EventAuthorDto.builder()
                .id(userVO.getId())
                .name(userVO.getName())
                .build();
    }
}
