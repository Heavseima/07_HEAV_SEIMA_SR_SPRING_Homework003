package org.example.eventticketingsystem.service;

import jakarta.validation.Valid;
import org.example.eventticketingsystem.model.entity.Event;
import org.example.eventticketingsystem.model.entity.Event;
import org.example.eventticketingsystem.model.request.EventRequest;

import java.util.List;

public interface EventService {
    List<Event> getAllEvent(Long page, Long size);

    Event getEventById(Long id);

    Event createEvent(EventRequest request);

    Event updateEvent(@Valid EventRequest request, Long id);

    void deleteEvent(Long id);
}
