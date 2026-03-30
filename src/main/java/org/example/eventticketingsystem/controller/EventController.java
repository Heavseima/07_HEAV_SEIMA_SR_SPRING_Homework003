package org.example.eventticketingsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.eventticketingsystem.model.entity.Event;
import org.example.eventticketingsystem.model.request.EventRequest;
import org.example.eventticketingsystem.model.response.ApiResponse;
import org.example.eventticketingsystem.service.EventService;
import org.example.eventticketingsystem.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ApiResponse<List<Event>> getAllEvent(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long size) {
        return ResponseUtil.success("retrieved event", eventService.getAllEvent(page, size));
    }

    @GetMapping("/{event-id}")
    public ApiResponse<Event> getEventById(@PathVariable("event-id") Long id) {
        Event event = eventService.getEventById(id);
        return ResponseUtil.success("retrieved event of id " + event.getEventId(), event);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Event>> createEvent(@Valid @RequestBody EventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseUtil.created("event", eventService.createEvent(request)));
    }

    @PutMapping("/{event-id}")
    public ApiResponse<Event> updateEvent(@Valid @RequestBody EventRequest request, @PathVariable("event-id") Long id) {
        return ResponseUtil.success("updated event", eventService.updateEvent(request, id));
    }

    @DeleteMapping("/{event-id}")
    public ApiResponse<Void> createEvent(@PathVariable("event-id") Long id) {
        eventService.deleteEvent(id);
        return ResponseUtil.deleted("event of id " + id);
    }
}
