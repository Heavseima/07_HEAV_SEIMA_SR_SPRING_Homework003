package org.example.eventticketingsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.eventticketingsystem.model.entity.Attendee;
import org.example.eventticketingsystem.model.request.AttendeeRequest;
import org.example.eventticketingsystem.model.request.AttendeeRequestUpdate;
import org.example.eventticketingsystem.model.response.ApiResponse;
import org.example.eventticketingsystem.service.AttendeeService;
import org.example.eventticketingsystem.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Attendee>>> getAllAttendee(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long size) {
        return ResponseEntity.ok(ResponseUtil.success("retrieved attendee", attendeeService.getAllAttendee(page, size)));
    }

    @GetMapping("{attendee-id}")
    public ResponseEntity<ApiResponse<Attendee>> getAttendeeById(@PathVariable("attendee-id") Long id) {
        Attendee attendee = attendeeService.getAttendeeById(id);
        return ResponseEntity.ok(ResponseUtil.success("retrieved attendee of id " + attendee.getAttendeeId(), attendee));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Attendee>> createAttendee(@Valid @RequestBody AttendeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseUtil.success("retrieved attendee", attendeeService.createAttendee(request)));
    }

    @PutMapping("{attendee-id}")
    public ResponseEntity<ApiResponse<Attendee>> updateAttendeeById(@PathVariable("attendee-id") Long id, @Valid @RequestBody AttendeeRequestUpdate request) {
        Attendee attendee = attendeeService.updateAttendeeById(id, request);
        return ResponseEntity.ok(ResponseUtil.success("updated attendee of id " + attendee.getAttendeeId(), attendee));
    }

    @DeleteMapping("{attendee-id}")
    public ResponseEntity<ApiResponse<Void>> deleteAttendeeById(@PathVariable("attendee-id") Long id) {
        attendeeService.deleteAttendee(id);
        return ResponseEntity.ok(ResponseUtil.deleted("attendee of id " + id));
    }
}