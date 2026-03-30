package org.example.eventticketingsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.eventticketingsystem.exception.ConflictException;
import org.example.eventticketingsystem.exception.NotFoundException;
import org.example.eventticketingsystem.model.entity.Venue;
import org.example.eventticketingsystem.model.request.VenueRequest;
import org.example.eventticketingsystem.repository.VenueRepository;
import org.example.eventticketingsystem.service.VenueService;
import org.example.eventticketingsystem.util.BadRequestUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements VenueService {

    private final VenueRepository venueRepository;

    @Override
    public List<Venue> getAllVenue(Long page, Long size) {

        new BadRequestUtil()
                .add(page < 1, "page", "page must be greater than 0")
                .add(size < 1, "size", "size must be greater than 0")
                .validate();

        Long offset= (page-1) * size;

        return venueRepository.getAllVenue(offset, size);
    }

    @Override
    public Venue getVenueById(Long id) {

        new BadRequestUtil()
                .add(id < 1, "venueId", "id must be greater than 0")
                .validate();

        Venue venue = venueRepository.getVenueById(id);

        if (venue == null) throw new NotFoundException("venue of id " + id);

        return venue;
    }

    @Override
    public Venue createVenue(VenueRequest request) {
        if (venueRepository.validateVenue(request) != null) throw new ConflictException("Venue name already existed");
        return venueRepository.createVenue(request);
    }

    @Override
    public Venue updateVenue(VenueRequest request, Long id) {

        new BadRequestUtil()
                .add(id < 1, "venueId", "id must be greater than 0")
                .validate();

        if (venueRepository.validateVenue(request) != null) throw new ConflictException("Venue already existed");

        Venue venue = venueRepository.updateVenue(request, id);

        if (venue == null) throw new NotFoundException("venue of id " + id);

        return venue;
    }

    @Override
    public void deleteVenue(Long id) {
        new BadRequestUtil()
                .add(id < 1, "venueId", "id must be greater than 0")
                .validate();

        if (venueRepository.getVenueById(id) == null) throw new NotFoundException("venue of id " + id);

        venueRepository.deleteVenue(id);
    }
}
