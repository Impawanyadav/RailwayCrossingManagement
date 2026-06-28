package in.py.main.Dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AutoAssignRequestDto {
	@NotNull(message = "Crossing Id is required")
	private Long crossingId;
	
	@NotNull(message = "Duty is required")
	private LocalDate dutyDate;
	
	@NotBlank(message = "Shift is required")
	private String shift;

}
