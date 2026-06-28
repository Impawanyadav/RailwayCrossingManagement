package in.py.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.py.main.Dto.CrossingDashboardDto;
import in.py.main.Dto.RailwayCrossingDto;
import in.py.main.entity.RailwayCrossing;

@Service
public interface RailwayCrossingService {
	public List<RailwayCrossingDto> getCrossings(Long id);
	public CrossingDashboardDto detailCrossing(Long id);
	public String addCrossing(RailwayCrossingDto dto);
	
	
}
