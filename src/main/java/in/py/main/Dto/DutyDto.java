package in.py.main.Dto;

import java.time.LocalDate;

import in.py.main.entity.RailwayCrossing;
import lombok.Data;

@Data
public class DutyDto {
	
	private UserDto user;
	private RailwayCrossingDto railwayCrossing;
	private String shift;
    private LocalDate dutyDate;
	
	
	
	
	
	private String status;

}
