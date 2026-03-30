package org.example.eventticketingsystem.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueRequest {

    @NotBlank(message = "venue name cannot be blank")
    @Size(min = 3, max = 50, message = "venue name must be between 3 and 50 characters")
    private String venueName;

    @NotBlank(message = "venue location cannot be blank")
    @Size(min = 3, max = 50, message = "location name must be between 3 and 50 characters")
    private String location;
}
