package in.py.main.Dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DutyAssignDto {
	@NotNull(message = "Crossing ID is required")
    private Long crossingId;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Duty date is required")
    @FutureOrPresent(message = "Duty date must be today or in the future")
    private LocalDate dutyDate;

    @NotBlank(message = "Shift is required (e.g., MORNING, NIGHT)")
    private String shift;

    private String status;

}
