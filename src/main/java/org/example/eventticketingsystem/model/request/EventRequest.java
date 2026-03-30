package org.example.eventticketingsystem.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    @NotBlank(message = "Event name cannot be blank")
    @Size(min = 3, max = 100, message = "Event name must be between 3 and 100 characters")
    private String eventName;

    @NotNull(message = "Event date cannot be blank")
    @Future(message = "Event date must be in the future")
    private LocalDate evenDate;

    @NotNull(message = "Venue information cannot be blank")
    @Valid
    private Long venueId;

    @NotEmpty(message = "An event must have at least one attendee")
    @Valid
    private List<Long> attendeeIds;
}
