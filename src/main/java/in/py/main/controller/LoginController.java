package in.py.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class LoginController {
	
	
	@GetMapping("/login")
    public String showLoginForm() {
        return "login"; 
    }

    
    @GetMapping("/admin/add-gateman")
    public String showAddGatemanForm() {
        return "admin-add-gateman"; 
    }
    @GetMapping("/admin/add-crossing")
    public String showAddCrossingForm() {
        return "admin-add-crossing"; 
    }
    @GetMapping("/admin/add-train")
    public String showAddTrainForm() {
        return "admin-add-train"; 
    }
    @GetMapping("/admin/assign-duty")
    public String showAssignDutyForm() {
        return "admin-assign-duty"; 
    }
    
    @GetMapping("/admin/add-crossing-log")
    public String showAddCrossingLogForm() {
        return "admin-add-crossing-log"; 
    }

}
