package in.py.main.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import in.py.main.Dto.CrossingDashboardDto;
import in.py.main.Dto.CrossingLogDto;
import in.py.main.Dto.DutyDto;
import in.py.main.Dto.RailwayCrossingDto;
import in.py.main.entity.CrossingLog;
import in.py.main.entity.DutyAssign;
import in.py.main.entity.RailwayCrossing;
import in.py.main.repository.CrossingLogRepository;
import in.py.main.repository.DutyAssignRepository;
import in.py.main.repository.RailwayCrossingRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class RailwayCrossingServiceImp implements RailwayCrossingService{
	private final RailwayCrossingRepository railwayCrossingRepository;
	private final ModelMapper modelMapper;
	private final DutyAssignRepository dutyAssignRepository;
	private final CrossingLogRepository crossingLogRepository;

	@Override
	public List<RailwayCrossingDto> getCrossings(Long id) {
		// TODO Auto-generated method stub
    List<RailwayCrossing> rawCrossings = railwayCrossingRepository.searchByIdOrLoadAll(id);
        
        return rawCrossings.stream()
        		.map(crossing-> modelMapper.map(crossing, RailwayCrossingDto.class))
        		.collect(Collectors.toList());
       
            
	}

	@Override
	public CrossingDashboardDto detailCrossing(Long id) {
		// TODO Auto-generated method stub
		
		RailwayCrossing rawCrossing= railwayCrossingRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Crossing not found with Id:" + id));
		
		
		DutyAssign activeDuty=dutyAssignRepository.findActiveDutyByCrossingId(id)
				.orElse(null);
		
		List<CrossingLog> rawLogs=crossingLogRepository.findByRailwayCrossing_Id(id);
		
		RailwayCrossingDto crossingDto=modelMapper.map(rawCrossing, RailwayCrossingDto.class);
		
		DutyDto dutyDto=null;
		if(activeDuty!=null)
		{
			dutyDto=modelMapper.map(activeDuty, DutyDto.class);
		}
		
		List<CrossingLogDto> logDtos=rawLogs.stream()
				.map(log->modelMapper.map(log, CrossingLogDto.class))
				.collect(Collectors.toList());
		
		CrossingDashboardDto dashboard=new CrossingDashboardDto();
		
		dashboard.setRailwayCrossing(crossingDto);
		dashboard.setDutyAssign(dutyDto);
		dashboard.setCrossingLog(logDtos);
		return dashboard;
	}

	@Override
	@Transactional
	public String addCrossing(RailwayCrossingDto dto) {
		// TODO Auto-generated method stub
		
		RailwayCrossing crossing=modelMapper.map(dto, RailwayCrossing.class);
		
		if(crossing.getStatus()==null || crossing.getStatus().isEmpty()) {
			crossing.setStatus("OPEN");
		}
		
		railwayCrossingRepository.save(crossing);
		return "Success";
	}

	

}
