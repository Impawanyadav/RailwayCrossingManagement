 package in.py.main.Dto;

import java.util.List;

import lombok.Data;

@Data
public class CrossingDashboardDto {
	
	private RailwayCrossingDto railwayCrossing;
	private DutyDto dutyAssign;
	
	private List<CrossingLogDto> crossingLog;


}
