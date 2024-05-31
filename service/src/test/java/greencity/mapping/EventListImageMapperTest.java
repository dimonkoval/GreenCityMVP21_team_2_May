package greencity.mapping;

import greencity.dto.event.model.EventImage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class EventListImageMapperTest {
    @InjectMocks
    EventListEventImageMapper mapper;

    @Test
    void convert() {
        String path1 = "path";
        String path2 = "some path";
        String path3 = "another path";
        List<EventImage> expectedList = new ArrayList<>();
        expectedList.add(EventImage.builder()
                .imagePath(path1)
                .isMain(false)
                .build());
        expectedList.add(EventImage.builder()
                .imagePath(path2)
                .isMain(false)
                .build());
        expectedList.add(EventImage.builder()
                .imagePath(path3)
                .isMain(false)
                .build());

        String[] imagePathsToConvert = {path1, path2, path3};
        assertEquals(expectedList, mapper.convert(imagePathsToConvert));
    }
}
