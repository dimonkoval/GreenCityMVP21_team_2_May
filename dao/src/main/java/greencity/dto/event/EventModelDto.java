package greencity.dto.event;

import greencity.annotations.ValidEventDateTime;
import greencity.entity.WebPage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventModelDto {

    private Long id;

    @NotBlank
    @Size(max = 70)
    private String title;

//    @OneToMany
    @ValidEventDateTime
    @Size(max = 7, min = 1)
    private List<EventDateTime> eventDateTimes;

    private List<Boolean> isOnline;

    @NotBlank
    @Size(min = 20, max = 63206)
    private String description;

    private boolean isEventOpen = true;

//    @ManyToMany
    private List<EventLocation> eventLocations;

//    @ManyToMany
    private List<WebPage> webPage;

//    @OneToMany
    private List<EventImage> eventImages;

}
