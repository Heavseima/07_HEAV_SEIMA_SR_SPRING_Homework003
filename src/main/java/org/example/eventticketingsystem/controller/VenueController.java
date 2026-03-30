package org.example.eventticketingsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.eventticketingsystem.model.entity.Venue;
import org.example.eventticketingsystem.model.request.VenueRequest;
import org.example.eventticketingsystem.model.response.ApiResponse;
import org.example.eventticketingsystem.service.VenueService;
import org.example.eventticketingsystem.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    @GetMapping
    public ApiResponse<List<Venue>> getAllVenue(@RequestParam(defaultValue = "1") Long page, @RequestParam(defaultValue = "10") Long size) {
        return ResponseUtil.success("retrieved venue", venueService.getAllVenue(page, size));
    }

    @GetMapping("/{venue-id}")
    public ApiResponse<Venue> getVenueById(@PathVariable("venue-id") Long id) {
        Venue venue = venueService.getVenueById(id);
        return ResponseUtil.success("retrieved venue of id " + venue.getVenueId(), venue);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Venue>> createVenue(@Valid @RequestBody VenueRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseUtil.created("venue", venueService.createVenue(request)));
    }

    @PutMapping("/{venue-id}")
    public ApiResponse<Venue> updateVenue(@Valid @RequestBody VenueRequest request, @PathVariable("venue-id") Long id) {
        return ResponseUtil.success("updated venue", venueService.updateVenue(request, id));
    }

    @DeleteMapping("/{venue-id}")
    public ApiResponse<Void> createVenue(@PathVariable("venue-id") Long id) {
        venueService.deleteVenue(id);
        return ResponseUtil.deleted("venue of id " + id);
    }
}
