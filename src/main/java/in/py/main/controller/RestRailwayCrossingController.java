package in.py.main.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.py.main.Dto.CrossingDashboardDto;
import in.py.main.Dto.CrossingLogEntryGatemanDtoResponse;
import in.py.main.Dto.RailwayCrossingDto;
import in.py.main.service.RailwayCrossingService;
import lombok.AllArgsConstructor;



@RestController
@AllArgsConstructor
@RequestMapping("/api/crossings")
public class RestRailwayCrossingController {
	
	private final RailwayCrossingService railwayCrossingService;
	
	@GetMapping(value = {"/search", "/search/{id}"})
	public ResponseEntity<List<RailwayCrossingDto>> searchCrossing(@PathVariable(required = false) Long id){
		List<RailwayCrossingDto> crossing= railwayCrossingService.getCrossings(id);    
		return ResponseEntity.ok(crossing);
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<CrossingDashboardDto> detailCrossing(@PathVariable Long id){
		CrossingDashboardDto railwayCrossingDto=railwayCrossingService.detailCrossing(id);
		return ResponseEntity.ok(railwayCrossingDto);
	}
	
	@GetMapping("/todayLog/{id}")
	public ResponseEntity<List<CrossingLogEntryGatemanDtoResponse>>todayLog(@PathVariable Long id)
	{
		Long crossingId=id;
		List<CrossingLogEntryGatemanDtoResponse> responses=railwayCrossingService.todayLog(crossingId);
		
		return ResponseEntity.ok(responses);
		
	}
	
	
	
	@GetMapping("/available")
	public ResponseEntity<List<RailwayCrossingDto>> getAvailableCrossings(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dutyDate,
			@RequestParam String shift){
		List<RailwayCrossingDto> availableCrossingDtos=railwayCrossingService.getAvailableCrossings(dutyDate, shift);
		return ResponseEntity.ok(availableCrossingDtos);
		
	}

}
