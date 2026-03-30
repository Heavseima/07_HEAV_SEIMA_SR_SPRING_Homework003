package org.example.eventticketingsystem.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeRequest {

    @NotBlank(message = "attendee name cannot be blanked")
    @Size(min = 3, max = 50, message = "attendee name must be between 3 and 50 characters")
    private String attendeeName;

    @NotBlank(message = "attendee email cannot be blanked")
    @Email(message = "Invalid email format")
    private String email;
}
