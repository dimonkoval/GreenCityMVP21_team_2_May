package greencity.controller;

import greencity.annotations.ApiPageable;
import greencity.annotations.CurrentUser;
import greencity.annotations.ImageValidation;
import greencity.constant.HttpStatuses;
import greencity.constant.SwaggerExampleModel;
import greencity.dto.event.EventRequestSaveDto;
import greencity.dto.event.EventResponseDto;
import greencity.dto.event.model.EventModelDto;
import greencity.dto.user.UserVO;
import greencity.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;

    /**
     * Method for creating {@link EventModelDto}
     *
     * @param eventRequestSaveDto - dto for {@link EventModelDto} entity.
     * @param images - array of {@link MultipartFile} images.
     * @param user - current user {@link UserVO}.
     * @return dto {@link EventModelDto} instance.
     */
    @Operation(summary = "Create Event.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = HttpStatuses.CREATED,
                    content = @Content(schema = @Schema(implementation = EventResponseDto.class))),
            @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
            @ApiResponse(responseCode = "401", description = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(responseCode = "415", description = HttpStatuses.UNSUPPORTED_MEDIA_TYPE)
    })
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EventResponseDto> save(
            @Parameter(description = SwaggerExampleModel.ADD_EVENT, required = true)
            @RequestPart EventRequestSaveDto eventRequestSaveDto,                       // add @ValidationClass
            @Parameter(description = "Upload array of images for event.") MultipartFile[] images,
            @Parameter(hidden = true) @CurrentUser UserVO user
            ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                eventService.save(eventRequestSaveDto, images, 0, user));
    }

    /**
     * Method for getting all events by page.
     *
     * @return PageableDto of {@link EventResponseDto} instances.
     */
    @Operation(summary = "Find all events by page.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
            @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
            @ApiResponse(responseCode = "401", description = HttpStatuses.UNAUTHORIZED)
    })
    @GetMapping("/all")
    @ApiPageable
    public ResponseEntity<List<EventResponseDto>> findAll(@Parameter(hidden = true) Pageable page) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findAll(page));
    }

    /**
     * Method for getting event by id.
     *
     * @return {@link EventResponseDto} instance.
     */
    @Operation(summary = "Get event by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
            @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST),
            @ApiResponse(responseCode = "401", description = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(eventService.findById(id));
    }

    /**
     * Method for getting eco news by authorised user.
     *
     * @return list of {@link EventResponseDto} instances.
     */
    @Operation(summary = "Get events by user id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HttpStatuses.OK),
            @ApiResponse(responseCode = "401", description = HttpStatuses.UNAUTHORIZED)
    })
    @ApiPageable
    @GetMapping("/author/{userId}")
    public ResponseEntity<List<EventResponseDto>> getEventByAuthorId(@PathVariable Long userId,
                                                                   @Parameter(hidden = true) Pageable page ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(eventService.findAllByAuthor(page, userId));
    }
}
