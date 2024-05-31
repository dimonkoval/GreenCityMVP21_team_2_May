package greencity.dto.event;

import greencity.annotations.ValidEventDateTime;
import greencity.entity.User;
import greencity.entity.WebPage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventModelDto {
    //TODO need to be modified due to requirements and implementations
//    @Id
    private Long id;

    @NotBlank
    @Size(max = 70)
    private String title;

//    @OneToMany
    @NotNull
    @ValidEventDateTime
    @Size(max = 7, min = 1)
    private List<EventDateTime> eventDateTimes;

    @NotNull
    private List<Boolean> isOnline;

    @NotBlank
    @Size(min = 20, max = 63206)
    private String description;

    private boolean isEventOpen = true;

//    @ManyToMany
    private List<EventLocation> eventLocations = new ArrayList<>();

//    @OneToMany
    private List<EventImage> eventImages = new ArrayList<>();

    //@ManyToOne
    @NotNull
    private User author;
}
