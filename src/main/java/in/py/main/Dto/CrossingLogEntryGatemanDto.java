package in.py.main.Dto;


import java.time.LocalTime;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrossingLogEntryGatemanDto {
	
	@NotNull(message = "Crossing ID is required")
    private Long crossingId;

    @NotNull(message = "Train number is required")
    private Integer trainNumber;

    @NotNull(message = "Gateman Id is required")
    private Long userId;
    
    


    


}
