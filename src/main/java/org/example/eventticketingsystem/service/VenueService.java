package org.example.eventticketingsystem.service;

import jakarta.validation.Valid;
import org.example.eventticketingsystem.model.entity.Venue;
import org.example.eventticketingsystem.model.request.VenueRequest;

import java.util.List;

public interface VenueService {

    List<Venue> getAllVenue(Long page, Long size);

    Venue getVenueById(Long id);

    Venue createVenue(VenueRequest request);

    Venue updateVenue(@Valid VenueRequest request, Long id);

    void deleteVenue(Long id);
}
