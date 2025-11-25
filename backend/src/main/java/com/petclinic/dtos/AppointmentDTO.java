package com.petclinic.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record AppointmentDTO(
        Long id,
        @NotNull Long petId,
        @NotNull LocalDateTime startTime,
        @Size(max=255) String reason,
        @Size(max=20) String status
) {}