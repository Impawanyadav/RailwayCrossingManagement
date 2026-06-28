package in.py.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.py.main.Dto.AllUpcomingDutyDto;
import in.py.main.service.GatemanService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/userDashboard")
public class RestUserDashbordController {
	
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
	@PostMapping("/{crossingId}/gate")
	public ResponseEntity<String> toggleGate(
			@PathVariable Long crossingId, 
			@RequestParam String action){
		String response=gatemanService.operateGate(crossingId, action);
		return ResponseEntity.ok(response);
	}
	

}
