package greencity.mapping;

import greencity.dto.event.model.EventImage;
import org.modelmapper.AbstractConverter;

import java.util.ArrayList;
import java.util.List;

public class EventListEventImageMapper extends AbstractConverter<String[], List<EventImage>> {

    @Override
    protected List<EventImage> convert(String[] imagePaths) {
        List<EventImage> toReturn = new ArrayList<>();
        for (String imagePath : imagePaths) {
            toReturn.add(EventImage.builder()
                    .imagePath(imagePath)
                    .isMain(false)
                    .build());
        }
        return toReturn;
    }
}
