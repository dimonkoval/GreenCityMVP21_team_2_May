package greencity.dto.event;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EventWebPage {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "web_page", nullable = false, unique = true)
    private String page;

//    @ManyToOne
    private List<EventModelDto> eventDtoList;          // we need to think about relationship !!!!!

}
