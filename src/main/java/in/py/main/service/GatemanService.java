package in.py.main.service;

import org.springframework.stereotype.Service;

import in.py.main.Dto.AllUpcomingDutyDto;


@Service
public interface GatemanService {
	
	public AllUpcomingDutyDto gatemanDashboard(Long id);
	
	public String punchInNewGateman(Long crossingId, Long newGatemanId);
	
	public String operateGate(Long crossingId, String action) throws IllegalArgumentException;

}
