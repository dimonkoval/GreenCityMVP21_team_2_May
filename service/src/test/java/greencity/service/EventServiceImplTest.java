package greencity.service;

import greencity.ModelUtils;
import greencity.client.RestClient;
import greencity.dto.event.*;
import greencity.dto.user.UserVO;
import greencity.entity.User;
import greencity.entity.event.EventAddress;
import greencity.entity.event.EventDayInfo;
import greencity.entity.event.EventImage;
import greencity.entity.event.Event;
import greencity.enums.EventStatus;
import greencity.exception.exceptions.WrongIdException;
import greencity.message.EventEmailMessage;
import greencity.repository.EventRepo;
import greencity.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EventServiceImplTest {

    @Mock
    EventRepo eventRepo;

    @Mock
    ModelMapper modelMapper;

    @Mock
    FileService fileService;

    @Mock
    UserRepo userRepo;

    @InjectMocks
    EventServiceImpl eventService;

    @Mock
    Pageable pageable;

    @Mock
    ExecutorService emailThreadPool;

    @Mock
    RestClient restClient;

    EventRequestSaveDto eventRequestSaveDto;
    EventSaveDayInfoDto dayInfoRequest1;
    EventSaveDayInfoDto dayInfoRequest2;
    EventResponseDto eventResponseDto;
    EventResponseDayInfoDto dayInfoResponse1;
    EventResponseDayInfoDto dayInfoResponse2;
    User user;
    Event eventForResponse;
    Event eventForRequest;
    EventDayInfo dayInfo1;
    EventDayInfo dayInfo2;
    EventImage image1;
    EventImage image2;
    MockMultipartFile file1;
    MockMultipartFile file2;

    @BeforeEach
    void setup() {
        dayInfoRequest1 = EventSaveDayInfoDto.builder()
                .dayNumber(1)
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 2, 17, 0, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 2, 17, 23, 59), ZoneId.systemDefault()))
                .status(EventStatus.OFFLINE)
                .address(EventAddressDto.builder()
                        .latitude(BigDecimal.TEN)
                        .longitude(BigDecimal.TWO)
                        .addressEn("location address")
                        .addressUa("адреса місця")
                        .build())
                .build();
        dayInfoRequest2 = EventSaveDayInfoDto.builder()
                .dayNumber(2)
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 3, 17, 0, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 3, 17, 23, 59), ZoneId.systemDefault()))
                .status(EventStatus.ONLINE)
                .link("location link")
                .build();
        eventRequestSaveDto = EventRequestSaveDto.builder()
                .title("Event title")
                .description("Event description")
                .isOpen(true)
                .mainImageNumber(1)
                .daysInfo(List.of(dayInfoRequest1, dayInfoRequest2))
                .build();

        dayInfoResponse1 = EventResponseDayInfoDto.builder()
                .dayNumber(1)
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 2, 17, 0, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 2, 17, 23, 59), ZoneId.systemDefault()))
                .status(EventStatus.OFFLINE)
                .address(EventAddressDto.builder()
                        .latitude(BigDecimal.TEN)
                        .longitude(BigDecimal.TWO)
                        .addressEn("location address")
                        .addressUa("адреса місця")
                        .build())
                .build();
        dayInfoResponse2 = EventResponseDayInfoDto.builder()
                .dayNumber(2)
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 3, 17, 0, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 3, 17, 23, 59), ZoneId.systemDefault()))
                .status(EventStatus.ONLINE)
                .link("location link")
                .build();
        eventResponseDto = EventResponseDto.builder()
                .id(1L)
                .title(eventRequestSaveDto.getTitle())
                .isOpen(eventRequestSaveDto.isOpen())
                .description(eventRequestSaveDto.getDescription())
                .dateTimes(List.of(dayInfoResponse1, dayInfoResponse2))
                .author(EventAuthorDto.builder().id(1L).name("test name").build())
                .images(List.of(
                        EventImageDto.builder().imagePath("imagePath").isMain(true).build(),
                        EventImageDto.builder().imagePath("another path").isMain(false).build()))
                .build();

        user = ModelUtils.getUser();
        dayInfo1 = EventDayInfo.builder()
                .dayNumber(1)
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 2, 17, 0, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 2, 17, 23, 59), ZoneId.systemDefault()))
                .address(EventAddress.builder()
                        .latitude(BigDecimal.TEN)
                        .longitude(BigDecimal.TWO)
                        .addressEn("location address")
                        .addressUa("адреса місця")
                        .build())
                .build();
        dayInfo2 = EventDayInfo.builder()
                .dayNumber(2)
                .isAllDay(true)
                .startDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 3, 17, 0, 0), ZoneId.systemDefault()))
                .endDateTime(ZonedDateTime.of(LocalDateTime.of(2025, 3, 17, 23, 59), ZoneId.systemDefault()))
                .link("location link")
                .build();
        image1 = EventImage.builder().imagePath("imagePath").isMain(true).build();
        image2 = EventImage.builder().imagePath("another path").isMain(false).build();
        eventForResponse = Event.builder()
                .id(1L)
                .title(eventRequestSaveDto.getTitle())
                .isOpen(eventRequestSaveDto.isOpen())
                .description(eventRequestSaveDto.getDescription())
                .images(List.of(image1, image2))
                .author(this.user)
                .dayInfos(List.of(dayInfo1, dayInfo2))
                .build();
        eventForRequest = Event.builder()
                .title(eventRequestSaveDto.getTitle())
                .isOpen(eventRequestSaveDto.isOpen())
                .description(eventRequestSaveDto.getDescription())
                .dayInfos(List.of(dayInfo1, dayInfo2))
                .build();
        file1 = new MockMultipartFile("name", new byte[10]);
        file2 = new MockMultipartFile("another", new byte[1024]);
    }

    @Test
    void save() {
        UserVO userVO = ModelUtils.getUserVO();
        when(fileService.upload(file1)).thenReturn("imagePath");
        when(fileService.upload(file2)).thenReturn("another path");
        when(modelMapper.map(eventRequestSaveDto, Event.class)).thenReturn(eventForRequest);
        when(eventRepo.save(eventForRequest)).thenReturn(eventForResponse);
        when(modelMapper.map(eventForResponse, EventResponseDto.class)).thenReturn(eventResponseDto);
        when(modelMapper.map(userVO, User.class)).thenReturn(user);
        MockMultipartFile[] requestFiles = {file1, file2};
        assertEquals(eventResponseDto, eventService.save(eventRequestSaveDto, requestFiles, userVO));
    }

    @Test
    void findAll() {
        List<EventResponseDto> expected = List.of(eventResponseDto);
        when(eventRepo.findAll(pageable)).thenReturn(List.of(eventForResponse));
        when(modelMapper.map(eventForResponse, EventResponseDto.class)).thenReturn(eventResponseDto);
        assertEquals(expected, eventService.findAll(pageable));
    }

    @Test
    void findById() {
        when(eventRepo.findById(1L)).thenReturn(Optional.of(eventForResponse));
        when(modelMapper.map(eventForResponse, EventResponseDto.class)).thenReturn(eventResponseDto);
        assertEquals(eventResponseDto, eventService.findById(1L));
    }

    @Test
    void findById_ThrowsNotFound() {
        Optional<Event> notFound = Optional.empty();
        when(eventRepo.findById(2L)).thenReturn(notFound);
        assertThrows(WrongIdException.class, () -> eventService.findById(2L));
    }

    @Test
    void uploadImage() {
        String expected = "string";
        when(fileService.upload(file1)).thenReturn(expected);
        assertEquals(expected, eventService.uploadImage(file1));
    }

    @Test
    void uploadImage_ReturnNull() {
        assertEquals(null, eventService.uploadImage(null));
    }

    @Test
    void uploadImages() {
        String[] expected = {"path", "another"};
        when(fileService.upload(file1)).thenReturn(expected[0]);
        when(fileService.upload(file2)).thenReturn(expected[1]);
        MockMultipartFile[] files = {file1, file2};
        String[] actual = eventService.uploadImages(files);
        assertEquals(expected.length, actual.length);
        assertEquals(expected[0], actual[0]);
        assertEquals(expected[1], actual[1]);
    }

    @Test
    void uploadImages_ReturnEmptyArray() {
        String[] actual = eventService.uploadImages(null);
        assertEquals(0, actual.length);
    }


    @Test
    void findAllByAuthor() {
        List<EventResponseDto> expected = List.of(eventResponseDto);
        long authorId = 1;
        when(userRepo.findById(authorId)).thenReturn(Optional.of(ModelUtils.getUser()));
        when(eventRepo.findAllByAuthorId(pageable, authorId)).thenReturn(List.of(eventForResponse));
        when(modelMapper.map(eventForResponse, EventResponseDto.class)).thenReturn(eventResponseDto);
        assertEquals(expected, eventService.findAllByAuthor(pageable, authorId));
    }

    @Test
    void findAllByAuthor_ThrowsNotFound() {
        long authorId = 2;
        Optional<User> notExist = Optional.empty();
        when(userRepo.findById(authorId)).thenReturn(notExist);
        assertThrows(WrongIdException.class, () -> eventService.findAllByAuthor(pageable, authorId));
    }

    @Test
    void testSendEmailNotification() {
        EventEmailMessage eventEmailMessage = ModelUtils.getEventEmailMessage();
        RequestAttributes requestAttributes = mock(RequestAttributes.class);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        eventService.sendEmailNotification(eventEmailMessage);

        verify(restClient).sendEmailNotification(eventEmailMessage);
        assertEquals(requestAttributes, RequestContextHolder.getRequestAttributes());
    }
}