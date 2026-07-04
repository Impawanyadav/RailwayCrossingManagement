package in.py.main.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import in.py.main.Dto.CrossingDashboardDto;
import in.py.main.Dto.CrossingLogEntryGatemanDto;
import in.py.main.Dto.CrossingLogEntryGatemanDtoResponse;
import in.py.main.Dto.CrossingLogRequestDto;
import in.py.main.Dto.RailwayCrossingDto;
import in.py.main.entity.RailwayCrossing;

@Service
public interface RailwayCrossingService {
	public List<RailwayCrossingDto> getCrossings(Long id);
	public CrossingDashboardDto detailCrossing(Long id);
	public String addCrossing(RailwayCrossingDto dto);
	public List<RailwayCrossingDto> getAvailableCrossings(LocalDate dutyDate, String shift);
	public String addNewLogAdmin(CrossingLogRequestDto dto);
	public String closeGate(CrossingLogEntryGatemanDto dto);
	public String openGate(Long userId, Long crossingId);
	public List<CrossingLogEntryGatemanDtoResponse> todayLog(Long crossingId);
	
	
	
	
}
