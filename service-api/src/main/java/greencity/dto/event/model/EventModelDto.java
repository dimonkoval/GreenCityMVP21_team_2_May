package greencity.dto.event.model;

import greencity.annotations.ValidEventDateTime;
import greencity.dto.tag.TagVO;
import greencity.dto.user.UserVO;
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
    //@ValidLocation
    @Size(max = 7, min = 1)
    private List<EventDayInfo> dayInfos;

    @NotBlank
    @Size(min = 20, max = 63206)
    private String description;

    private boolean isOpen = true;

//    @OneToMany
    @Size(max = 5)
    private List<EventImage> images = new ArrayList<>();

    //@ManyToOne
    @NotNull
    private UserVO author;

    //@ManyToMany
    //private List<Tag> tags;
    private List<TagVO> tags;
}
