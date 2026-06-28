package in.py.main.Dto;

import java.util.List;

import lombok.Data;
@Data
public class AllUpcomingDutyDto {
	
	private UserDto user;
	

	private List<DutyDto> dutyAssign;

}
