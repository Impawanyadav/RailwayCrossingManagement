package in.py.main.Dto;

import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrossingLogRequestDto {
	@NotNull(message = "Crossing ID is required")
    private Long crossingId;

    @NotNull(message = "Train number is required")
    private Integer trainNumber;

    @NotEmpty(message = "Please select at least one day (e.g., MONDAY, TUESDAY)")
    private List<String> days;

    @NotNull(message = "Start time is required")
    private LocalTime closedFrom;

    @NotNull(message = "End time is required")
    private LocalTime closedTo;

}
