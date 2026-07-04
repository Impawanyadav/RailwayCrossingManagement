package in.py.main.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import in.py.main.Dto.CrossingDashboardDto;
import in.py.main.Dto.CrossingLogDto;
import in.py.main.Dto.CrossingLogEntryGatemanDto;
import in.py.main.Dto.CrossingLogEntryGatemanDtoResponse;
import in.py.main.Dto.CrossingLogRequestDto;
import in.py.main.Dto.DutyDto;
import in.py.main.Dto.RailwayCrossingDto;
import in.py.main.Dto.TrainDto;
import in.py.main.entity.CrossingLog;
import in.py.main.entity.DailyCrossingLogsRealTime;
import in.py.main.entity.DutyAssign;
import in.py.main.entity.RailwayCrossing;
import in.py.main.entity.Train;
import in.py.main.entity.User;
import in.py.main.repository.CrossingLogRepository;
import in.py.main.repository.DailyCrossingLogsRealTimeRepository;
import in.py.main.repository.DutyAssignRepository;
import in.py.main.repository.RailwayCrossingRepository;
import in.py.main.repository.TrainRepository;
import in.py.main.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class RailwayCrossingServiceImp implements RailwayCrossingService{
	private final RailwayCrossingRepository railwayCrossingRepository;
	private final ModelMapper modelMapper;
	private final DutyAssignRepository dutyAssignRepository;
	private final CrossingLogRepository crossingLogRepository;
	private final UserRepository userRepository;
	private final TrainRepository trainRepository;
	private final DailyCrossingLogsRealTimeRepository dailyCrossingLogsRealTimeRepository;

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
		
		List<CrossingLog> rawLogs=crossingLogRepository.findByRailwayCrossing_IdOrderByClosedFromAsc(id);
		
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
			crossing.setStatus("ACTIVE");
		}
		
		
		railwayCrossingRepository.save(crossing);
		return "Success";
	}

	@Override
	@Transactional
	public List<RailwayCrossingDto> getAvailableCrossings(LocalDate dutyDate, String shift) {
		// TODO Auto-generated method stub
		List<RailwayCrossing> available=railwayCrossingRepository.findAvailableCrossings(dutyDate, shift.toUpperCase());
		
		return available.stream()
				.map(crossing-> modelMapper.map(crossing, RailwayCrossingDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public String addNewLogAdmin(CrossingLogRequestDto dto) {
		// TODO Auto-generated method stub
		RailwayCrossing crossing=railwayCrossingRepository.findById(dto.getCrossingId())
				.orElseThrow(()-> new RuntimeException("Crossing not found"));
		
		Train trainDto=trainRepository.findByTrainNumber(dto.getTrainNumber())
				.orElseThrow(()-> new RuntimeException("Invalid Train Number"));
		
		Train train=modelMapper.map(trainDto, Train.class);
		 CrossingLog log = new CrossingLog();
	        log.setRailwayCrossing(crossing);
	        log.setTrain(train);
	        log.setDays(dto.getDays());
	        log.setClosedFrom(dto.getClosedFrom());
	        log.setClosedTo(dto.getClosedTo());

	        
	        crossingLogRepository.save(log);

	        return "Success! Schedule added for Train " + train.getTrainNumber() + " at Crossing ID " + crossing.getId() + ".";
	}

	@Override
	@Transactional
	public String closeGate(CrossingLogEntryGatemanDto dto) {
		// TODO Auto-generated method stub
		
		DutyAssign dutyAssign=dutyAssignRepository.findByUserIdAndRailwayCrossingIdAndStatus(dto.getUserId(),dto.getCrossingId(), "ONGOING")
				.orElseThrow(()-> new RuntimeException("Only assigned Gateman can add log"));
		
		CrossingLog train=crossingLogRepository.findByTrain_TrainNumberAndRailwayCrossing_Id(dto.getTrainNumber(), dto.getCrossingId())
				.orElseThrow(()-> new RuntimeException("Invalid Train number"));
		
		
		
		
		
		DailyCrossingLogsRealTime logsRealTime= new DailyCrossingLogsRealTime();
		
		logsRealTime.setRailwayCrossing(dutyAssign.getRailwayCrossing());
		logsRealTime.setTrain(train.getTrain());
		logsRealTime.setUser(dutyAssign.getUser());
		logsRealTime.setClosedFrom(LocalTime.now());
		
		logsRealTime.setDate(LocalDate.now());
		dailyCrossingLogsRealTimeRepository.save(logsRealTime);
		
				
		 return "Gate Closed successfully at " + logsRealTime.getClosedFrom();
	}

	
	@Override
	@Transactional
	public String openGate(Long userId, Long crossingId) {
		
		
		List<DailyCrossingLogsRealTime> activeLogs = dailyCrossingLogsRealTimeRepository
				.findByUserIdAndRailwayCrossingIdAndClosedToIsNull(userId, crossingId);
				
		
		if (activeLogs.isEmpty()) {
			throw new RuntimeException("Error: No closed gate found to open");
		}
		
		LocalTime openTime = LocalTime.now();
       
		for (DailyCrossingLogsRealTime log : activeLogs) {
			log.setClosedTo(openTime); 
		}
        
		
		dailyCrossingLogsRealTimeRepository.saveAll(activeLogs);

		return "Gate Opened successfully at " + openTime + ". Logged " + activeLogs.size() + " train(s).";
	}

	@Override
	public List<CrossingLogEntryGatemanDtoResponse> todayLog(Long crossingId) {
		// TODO Auto-generated method stub
		
		List<DailyCrossingLogsRealTime> logsRealTime=dailyCrossingLogsRealTimeRepository.findByRailwayCrossingIdAndDateAndClosedToIsNotNull(crossingId, LocalDate.now());
		
		List<CrossingLogEntryGatemanDtoResponse> dtoResponses=logsRealTime
				.stream()
				.map(log->modelMapper.map(log, CrossingLogEntryGatemanDtoResponse.class))
				.collect(Collectors.toList());
		
		return dtoResponses;
	}

	

}
