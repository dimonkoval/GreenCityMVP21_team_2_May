package greencity.mapping;

import greencity.dto.event.model.EventImage;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that used by {@link ModelMapper} to map {@link String[]}
 * into {@link List<EventImage>}.
 * @author Viktoriia Herchanivska
 */
public class EventListEventImageMapper extends AbstractConverter<String[], List<EventImage>> {

    /**
     * Method for converting {@link String[]} into {@link List<EventImage>}.
     *
     * @param imagePaths object to convert.
     * @return converted object.
     * @author Viktoriia Herchanivska
     */
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
