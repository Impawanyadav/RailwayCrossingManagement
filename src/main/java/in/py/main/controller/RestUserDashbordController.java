package in.py.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.py.main.Dto.AllUpcomingDutyDto;
import in.py.main.Dto.CrossingLogEntryGatemanDto;
import in.py.main.service.GatemanService;
import in.py.main.service.RailwayCrossingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/userDashboard")
public class RestUserDashbordController {
	private final RailwayCrossingService railwayCrossingService;
	private final GatemanService gatemanService;
	
	@GetMapping("/user/{id}")
	ResponseEntity<AllUpcomingDutyDto> UpcomingDuty(@PathVariable Long id)
	{
		AllUpcomingDutyDto allUpcomingDutyDto=gatemanService.gatemanDashboard(id);
		
		return ResponseEntity.ok(allUpcomingDutyDto);
	}
	
	@PostMapping("/{crossingId}/punch-in")
	public ResponseEntity<String> punchIn(
			@PathVariable Long crossingId,
			@RequestParam Long gatemanId){
		
		String responseMessage=gatemanService.punchInNewGateman(crossingId, gatemanId);
				return ResponseEntity.ok(responseMessage);
		
	}
	@PostMapping("/close")
	public ResponseEntity<String> openGate(@Valid @RequestBody CrossingLogEntryGatemanDto dto ){
		String msgString=railwayCrossingService.closeGate(dto);
		return ResponseEntity.ok(msgString);
		
	}
	
	 @PutMapping("/open")
	    public ResponseEntity<String> openGate(
	            @RequestParam Long userId, 
	            @RequestParam Long crossingId){
		 String mString=railwayCrossingService.openGate(userId, crossingId);
		 return ResponseEntity.ok(mString);
		 
		 
	 }
	    
	/*@PostMapping("/{crossingId}/gate")
	public ResponseEntity<String> toggleGate(
			@PathVariable Long crossingId, 
			@RequestParam String action){
		String response=gatemanService.operateGate(crossingId, action);
		return ResponseEntity.ok(response);
	}*/
	

}
