package in.py.main.Dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class CrossingLogEntryGatemanDtoResponse {
	
	private LocalTime closedFrom;
	private LocalTime closedTo;
	private TrainDto train;

}
