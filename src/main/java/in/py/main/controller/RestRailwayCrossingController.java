package in.py.main.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.py.main.Dto.CrossingDashboardDto;
import in.py.main.Dto.RailwayCrossingDto;
import in.py.main.service.RailwayCrossingServiceImp;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/crossings")
public class RestRailwayCrossingController {
	
	private final RailwayCrossingServiceImp railwayCrossingServiceImp;
	
	@GetMapping(value = {"/search", "/search/{id}"})
	public ResponseEntity<List<RailwayCrossingDto>> searchCrossing(@PathVariable(required = false) Long id){
		List<RailwayCrossingDto> crossing= railwayCrossingServiceImp.getCrossings(id);
		return ResponseEntity.ok(crossing);
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<CrossingDashboardDto> detailCrossing(@PathVariable Long id){
		CrossingDashboardDto railwayCrossingDto=railwayCrossingServiceImp.detailCrossing(id);
		return ResponseEntity.ok(railwayCrossingDto);
	}

}
