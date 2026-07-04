package in.py.main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.py.main.Dto.AutoAssignRequestDto;
import in.py.main.Dto.CrossingLogRequestDto;
import in.py.main.Dto.RailwayCrossingDto;
import in.py.main.Dto.TrainDto;
import in.py.main.service.DutyAssignService;
import in.py.main.service.RailwayCrossingService;
import in.py.main.service.TrainService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/")
public class AdminRestController {
	
	private final RailwayCrossingService railwayCrossingService;
	private final TrainService trainService;
	private final DutyAssignService dutyAssignService;
	@PostMapping("/addCrossing")
	public ResponseEntity<String> createCrossing(@Valid @RequestBody RailwayCrossingDto crossingDto){
		String response= railwayCrossingService.addCrossing(crossingDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("/addTrain")
	public ResponseEntity<String> createTrain(@Valid @RequestBody TrainDto trainDto){
		String responseString=trainService.addTrain(trainDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(responseString);
	}
	
	@PostMapping("/assignDuty")
	public ResponseEntity<String>assignDuty(@Valid @RequestBody AutoAssignRequestDto dto){
		String msg=dutyAssignService.autoAssignGateman(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(msg);
	}
	
	@PostMapping("/addCrossingLog")
	public ResponseEntity<String>addNewCrossingLogAdmin(@Valid @RequestBody CrossingLogRequestDto dto){
		String msgString=railwayCrossingService.addNewLogAdmin(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(msgString);
		
	}

}
