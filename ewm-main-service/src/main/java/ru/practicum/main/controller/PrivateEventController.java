package ru.practicum.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.Marker;
import ru.practicum.main.dto.EventFullDto;
import ru.practicum.main.dto.EventReceivedDto;
import ru.practicum.main.dto.RequestDto;
import ru.practicum.main.dto.RequestShortDto;
import ru.practicum.main.dto.RequestShortUpdateDto;
import ru.practicum.main.dto.UpdateEventDto;
import ru.practicum.main.mapper.EventMapper;
import ru.practicum.main.mapper.RequestMapper;
import ru.practicum.main.model.Event;
import ru.practicum.main.model.Request;
import ru.practicum.main.model.RequestShortUpdate;
import ru.practicum.main.service.PrivateEventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users/{userId}")
public class PrivateEventController {

    private final PrivateEventService service;

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable long userId, @RequestBody @Validated EventReceivedDto eventReceivedDto) {
        Event event = service.createEvent(userId, EventMapper.toEvent(eventReceivedDto));
        return EventMapper.toEventFullDto(event);
    }

    @GetMapping("/events")
    public List<EventFullDto> getEventByUserId(@PathVariable long userId,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(defaultValue = "10") @Positive int size) {
        List<Event> list = service.getEventByUserId(userId, from, size);
        return EventMapper.toListEventFullDto(list);
    }

    @GetMapping("/events/{eventId}")
    public EventFullDto getEventByUserIdAndEventId(@PathVariable long userId, @PathVariable long eventId) {
        Event event = service.getEventByUserIdAndEventId(userId, eventId);
        return EventMapper.toEventFullDto(event);
    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto patchEvent(@PathVariable long userId, @PathVariable long eventId,
                                   @RequestBody @Validated(Marker.Update.class) UpdateEventDto receivedDto) {
        Event eventFull = service.patchEvent(userId, eventId, EventMapper.toEventFromUpdateEvent(receivedDto));
        return EventMapper.toEventFullDto(eventFull);
    }

    @GetMapping("/events/{eventId}/requests")
    public List<RequestDto> getRequestByUserIdAndEventId(@PathVariable long userId, @PathVariable long eventId) {
        List<Request> list = service.getRequestByUserIdAndEventId(userId, eventId);
        return RequestMapper.toListRequestDto(list);
    }

    @PatchMapping("/events/{eventId}/requests")
    public RequestShortUpdateDto patchRequestByOwnerUser(@PathVariable long userId, @PathVariable long eventId, @RequestBody RequestShortDto shortDto) {
        RequestShortUpdate requestShort = service.patchRequestByOwnerUser(userId, eventId, RequestMapper.toRequestShort(shortDto));
        return RequestMapper.toRequestShortUpdateDto(requestShort);
    }
}
