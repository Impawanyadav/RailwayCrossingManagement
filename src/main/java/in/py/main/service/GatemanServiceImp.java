package in.py.main.service;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import in.py.main.Dto.AllUpcomingDutyDto;
import in.py.main.Dto.DutyDto;
import in.py.main.Dto.UserDto;
import in.py.main.entity.DutyAssign;
import in.py.main.entity.RailwayCrossing;
import in.py.main.entity.User;
import in.py.main.repository.DutyAssignRepository;
import in.py.main.repository.RailwayCrossingRepository;
import in.py.main.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatemanServiceImp implements GatemanService{
	
	private final UserRepository userRepository;
	private final DutyAssignRepository dutyAssignRepository;
	private final ModelMapper modelMapper;
	private final RailwayCrossingRepository railwayCrossingRepository;

	@Override
	public AllUpcomingDutyDto gatemanDashboard(Long id) {
		// TODO Auto-generated method stub
		
		User user =userRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("User not found with Id:" + id));
		
		
		UserDto userDto=modelMapper.map(user, UserDto.class);
		
		List<DutyAssign> dutyDtos=dutyAssignRepository.findUpcomingDutiesByGatemanId(id);
		
		List<DutyDto> duty=dutyDtos.stream()
				.map(du->modelMapper.map(du, DutyDto.class))
				.collect(Collectors.toList());
		
		AllUpcomingDutyDto allUpcomingDuty=new AllUpcomingDutyDto();
		
		allUpcomingDuty.setUser(userDto);
		allUpcomingDuty.setDutyAssign(duty);
		return allUpcomingDuty;
	}

	@Override
	@Transactional
	public String punchInNewGateman(Long crossingId, Long newGatemanId) {
		// TODO Auto-generated method stub
		
		dutyAssignRepository.findActiveDutyByCrossingId(crossingId).ifPresent(activeDuty->{
			activeDuty.setStatus(("COMPLETED"));
			dutyAssignRepository.save(activeDuty);
		});
			
			DutyAssign upcomingDutyAssign=dutyAssignRepository.findTodayUpcomingDuty(crossingId, newGatemanId)
					.orElseThrow(()-> new RuntimeException("Punch In Failed"));
			
			upcomingDutyAssign.setStatus("OONGOING");
			dutyAssignRepository.save(upcomingDutyAssign);
			return "Successfully Punched In";
		
		
	}

	@Override
	public String operateGate(Long crossingId, String action) throws IllegalArgumentException {
		
		// TODO Auto-generated method stub
		
		RailwayCrossing crossing= railwayCrossingRepository.findById(crossingId)
				.orElseThrow(()-> new RuntimeException("Crossing Not Found"));
		
		if(action.equalsIgnoreCase("CLOSE")) {
			crossing.setAddress("CLOSED");
		}
		else if (action.equalsIgnoreCase("OPEN")) {
			crossing.setAddress("OPEN");
		}
		
		else throw new IllegalArgumentException("Gate Status must be 'OPEN' or 'CLOSE'");
		
		railwayCrossingRepository.save(crossing);
				
		return crossing.getAddress() + " is now " + crossing.getStatus();
	}
	

	
}
