package org.example.eventticketingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.eventticketingsystem.exception.ConflictException;
import org.example.eventticketingsystem.exception.NotFoundException;
import org.example.eventticketingsystem.model.entity.Attendee;
import org.example.eventticketingsystem.model.entity.Venue;
import org.example.eventticketingsystem.model.request.AttendeeRequest;
import org.example.eventticketingsystem.model.request.AttendeeRequestUpdate;
import org.example.eventticketingsystem.repository.AttendeeRepository;
import org.example.eventticketingsystem.service.AttendeeService;
import org.example.eventticketingsystem.util.BadRequestUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendeeServiceImpl implements AttendeeService {

    private final AttendeeRepository attendeeRepository;

    @Override
    public List<Attendee> getAllAttendee(Long page, Long size) {

        new BadRequestUtil()
                .add(page < 1, "page", "page must be greater than 0")
                .add(size < 1, "size", "size must be greater than 0")
                .validate();

        Long offset = (page-1) * size;

        return attendeeRepository.getAllAttendee(offset, size);
    }

    @Override
    public Attendee getAttendeeById(Long id) {

        new BadRequestUtil()
                .add(id < 1, "attendeeId", "id must be greater than 0")
                .validate();

        Attendee attendee = attendeeRepository.getAttendeeById(id);

        if (attendee == null) throw new NotFoundException("attendee of id " + id);

        return attendee;
    }

    @Override
    public Attendee createAttendee(AttendeeRequest request) {
        if (attendeeRepository.validateNameAttendee(request) != null) throw new ConflictException("Attendee name already existed");
        if (attendeeRepository.validateEmailAttendee(request) != null) throw new ConflictException("Attendee email already existed");
        return attendeeRepository.createAttendee(request);
    }

    @Override
    public Attendee updateAttendeeById(Long id, AttendeeRequestUpdate request) {
        new BadRequestUtil()
                .add(id < 1, "attendeeId", "id must be greater than 0")
                .validate();

        if (attendeeRepository.getAttendeeById(id) == null) throw new NotFoundException("attendee of id " + id);
        if (attendeeRepository.validateNameAttendeeUpdate(request) != null) throw new ConflictException("Attendee name already existed");

        return attendeeRepository.updateAttendeeById(id, request);
    }

    @Override
    public void deleteAttendee(Long id) {

        new BadRequestUtil()
                .add(id < 1, "attendeeId", "id must be greater than 0")
                .validate();

        if (attendeeRepository.getAttendeeById(id) == null) throw new NotFoundException("attendee of id " + id);

        attendeeRepository.deleteAttendee(id);
    }
}
