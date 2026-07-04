package in.py.main.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.py.main.Dto.AllUpcomingDutyDto;
import in.py.main.Dto.UserDto;
import in.py.main.entity.User;
import in.py.main.repository.UserRepository;
import in.py.main.service.GatemanService;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@Controller
public class DashboardController {
	private final UserRepository userRepository;
	private final GatemanService gatemanService;
	private final ModelMapper modelMapper;
	
	
	
	
	
	
	
	@GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard"; 
    }

   
    @GetMapping("/gateman/dashboard")
    public String gatemanDashboard(Model model, Principal principal) {
    	User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // 2. Map to DTO (Secure and prevents template crashes)
        UserDto userDto = modelMapper.map(user, UserDto.class);
        // Explicitly set the field that Thymeleaf is complaining about
        userDto.setDisplayRole(user.getRole()); 
        
        // 3. Add DTO to model
        model.addAttribute("userDto", userDto);
        return "gateman-dashboard"; 
    }
}
