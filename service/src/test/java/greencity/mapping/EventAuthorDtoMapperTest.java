package greencity.mapping;

import greencity.ModelUtils;
import greencity.dto.event.EventAuthorDto;
import greencity.dto.user.UserVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class EventAuthorDtoMapperTest {

    @InjectMocks
    private EventAuthorDtoMapper mapper;

    @Test
    void convert() {
        UserVO userVO = ModelUtils.getUserVO();

        EventAuthorDto expected = EventAuthorDto.builder()
                .id(userVO.getId())
                .name(userVO.getName())
                .build();

        assertEquals(expected, mapper.convert(userVO));
    }

}
