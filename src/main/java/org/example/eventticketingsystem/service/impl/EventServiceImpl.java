package org.example.eventticketingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.eventticketingsystem.exception.ConflictException;
import org.example.eventticketingsystem.exception.NotFoundException;
import org.example.eventticketingsystem.model.entity.Event;
import org.example.eventticketingsystem.model.request.EventRequest;
import org.example.eventticketingsystem.repository.EventAttendeeRepository;
import org.example.eventticketingsystem.repository.EventRepository;
import org.example.eventticketingsystem.service.EventService;
import org.example.eventticketingsystem.util.BadRequestUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventAttendeeRepository eventAttendeeRepository;

    @Override
    public List<Event> getAllEvent(Long page, Long size) {

        new BadRequestUtil()
                .add(page < 1, "page", "page must be greater than 0")
                .add(size < 1, "size", "size must be greater than 0")
                .validate();

        Long offset= (page-1) * size;

        return eventRepository.getAllEvent(offset, size);
    }

    @Override
    public Event getEventById(Long id) {

        new BadRequestUtil()
                .add(id < 1, "eventId", "id must be greater than 0")
                .validate();

        Event event = eventRepository.getEventById(id);

        if (event == null) throw new NotFoundException("event of id " + id);

        return event;
    }

    @Transactional
    @Override
    public Event createEvent(EventRequest request) {

        new BadRequestUtil()
                .add(!request.getEvenDate().toString().matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$"),
                        "evenDate", "Date must follow the pattern yyyy-MM-dd")
                .add(request.getVenueId() < 1, "venueId", "venue id must be greater than 0")
                .add(!request.getAttendeeIds().stream().filter(i -> i < 1).toList().isEmpty(), "attendeeIds", "attendee id must be greater than 0")
                .add(request.getAttendeeIds() == null || request.getAttendeeIds().isEmpty(),
                        "attendeeIds", "An event must have at least one attendee")
                .validate();

        Integer existingCount = eventRepository.countExistingEvents(
                request.getEventName(),
                request.getEvenDate()
        );

        if (existingCount != null && existingCount > 0) {
            throw new ConflictException("An event with this name already exists");
        }

        Event event = eventRepository.createEvent(request);

        for (var attendee : request.getAttendeeIds()) {
            eventAttendeeRepository.insertEventAttendee(attendee, event.getEventId());
        }

        return eventRepository.getEventById(event.getEventId());
    }

    @Transactional
    @Override
    public Event updateEvent(EventRequest request, Long id) {

        new BadRequestUtil()
                .add(id < 1, "eventId", "id must be greater than 0")
                .add(request.getVenueId() < 1, "venueId", "venue id must be greater than 0")
                .add(!request.getAttendeeIds().stream().filter(i -> i < 1).toList().isEmpty(), "attendeeIds", "attendee id must be greater than 0")
                .add(request.getAttendeeIds() == null || request.getAttendeeIds().isEmpty(),
                        "attendeeIds", "An event must have at least one attendee")
                .add(!request.getEvenDate().toString().matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$"),
                        "evenDate", "Date must follow the pattern yyyy-MM-dd")
                .validate();

        Integer existingCount = eventRepository.countExistingEvents(
                request.getEventName(),
                request.getEvenDate()
        );

        if (existingCount != null && existingCount > 0) {
            throw new ConflictException("An event with this name already exists");
        }

        if (eventRepository.getEventById(id) == null) throw new NotFoundException("event of id " + id);

        eventAttendeeRepository.deleteByEventId(id);

        Event event = eventRepository.updateEvent(request, id);

        for (var attendee : request.getAttendeeIds()) {
            eventAttendeeRepository.insertEventAttendee(attendee, event.getEventId());
        }

        return eventRepository.getEventById(id);
    }

    @Override
    public void deleteEvent(Long id) {
        new BadRequestUtil()
                .add(id < 1, "eventId", "id must be greater than 0")
                .validate();

        if (eventRepository.getEventById(id) == null) throw new NotFoundException("event of id " + id);

        eventRepository.deleteEvent(id);
    }
}
