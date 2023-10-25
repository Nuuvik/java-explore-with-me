package ru.practicum.main.service;

import ru.practicum.main.model.Event;
import ru.practicum.main.model.Request;
import ru.practicum.main.model.RequestShort;
import ru.practicum.main.model.RequestShortUpdate;
import ru.practicum.main.model.UpdateEvent;

import java.util.List;

public interface PrivateEventService {
    Event createEvent(long userId, Event event);

    List<Event> getEventByUserId(long userId, int from, int size);

    Event getEventByUserIdAndEventId(long userId, long eventId);

    Event patchEvent(long userId, long eventId, UpdateEvent updateEvent);

    List<Request> getRequestByUserIdAndEventId(long userId, long eventId);

    RequestShortUpdate patchRequestByOwnerUser(long userId, long eventId, RequestShort requestShort);

}
