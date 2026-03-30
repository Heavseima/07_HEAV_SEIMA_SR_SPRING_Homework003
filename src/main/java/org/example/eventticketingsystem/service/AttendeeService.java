package org.example.eventticketingsystem.service;

import jakarta.validation.Valid;
import org.example.eventticketingsystem.model.entity.Attendee;
import org.example.eventticketingsystem.model.request.AttendeeRequest;
import org.example.eventticketingsystem.model.request.AttendeeRequestUpdate;

import java.util.List;

public interface AttendeeService {
    List<Attendee> getAllAttendee(Long page, Long size);

    Attendee getAttendeeById(Long id);

    Attendee createAttendee(@Valid AttendeeRequest request);

    Attendee updateAttendeeById(Long id, @Valid AttendeeRequestUpdate request);

    void deleteAttendee(Long id);
}
