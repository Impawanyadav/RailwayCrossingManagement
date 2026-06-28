package in.py.main.Dto;

import java.time.LocalTime;
import java.util.List;

import in.py.main.entity.RailwayCrossing;
import in.py.main.entity.Train;
import lombok.Data;
@Data
public class CrossingLogDto {
	
	private List<String>days;
	private LocalTime closedFrom;
	private LocalTime closedTo;
	private TrainDto train;
	

}
