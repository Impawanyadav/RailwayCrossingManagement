package in.py.main.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.py.main.Dto.AutoAssignRequestDto;
import in.py.main.entity.DutyAssign;
import in.py.main.entity.RailwayCrossing;
import in.py.main.entity.User;
import in.py.main.repository.DutyAssignRepository;
import in.py.main.repository.RailwayCrossingRepository;
import in.py.main.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DutyAssignServiceImp implements DutyAssignService {
	
	private final DutyAssignRepository dutyAssignRepository;
	private final RailwayCrossingRepository railwayCrossingRepository;
	private final UserRepository userRepository;
	@Override
	@Transactional
	public String autoAssignGateman(AutoAssignRequestDto dto) {
	
		// TODO Auto-generated method stub
		String shiftString=dto.getShift().toUpperCase();
		boolean slotOccupied=dutyAssignRepository.existsByRailwayCrossingIdAndDutyDateAndShift(dto.getCrossingId(), dto.getDutyDate(), shiftString);
		if (slotOccupied) {
			throw new RuntimeException("Duty is already assigned");
		}
		RailwayCrossing railwayCrossing = railwayCrossingRepository.findById(dto.getCrossingId())
				.orElseThrow(()->new RuntimeException("Railway Crossing not found"));
		
		List<User> availableGateman=userRepository.findBestGatemanForDuty(dto.getCrossingId(), shiftString, dto.getDutyDate(), PageRequest.of(0, 1));
		
		if (availableGateman.isEmpty()) {
			throw new RuntimeException("No active Gateman on current shift" );
		}
		
		User allocatedGateman= availableGateman.get(0);
		DutyAssign automatedDuty = new DutyAssign();
		automatedDuty.setRailwayCrossing(railwayCrossing);
		automatedDuty.setUser(allocatedGateman);
		automatedDuty.setDutyDate(dto.getDutyDate());
		automatedDuty.setShift(shiftString);
		automatedDuty.setStatus("UPCOMING");
		
		dutyAssignRepository.save(automatedDuty);
	
	
		return "Success! Gateman Id: " + allocatedGateman.getId()+"  Name: " +allocatedGateman.getName() 
        + " Assigned to Crossing " + railwayCrossing.getAddress()+ " for the " + shiftString + " shift on " + automatedDuty.getDutyDate();
	}

}
