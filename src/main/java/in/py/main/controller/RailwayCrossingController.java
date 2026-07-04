package in.py.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/RailwayCrossing")
public class RailwayCrossingController {
	
	
	@GetMapping("/rcpage")
	public String getCrossing(){
		return "crossing";
	}
	
	@GetMapping("/crossing-detail/{id}")
	public String getCrossingDetailPage(@PathVariable Long id) {
	    return "crossing-detail"; 
	}
}
