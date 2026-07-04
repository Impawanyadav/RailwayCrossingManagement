package in.py.main.controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppNavigationController {

    @GetMapping("/home")
    public String dynamicHome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            
            for (GrantedAuthority authority : authorities) {
                String role = authority.getAuthority();
                
                // Spring Security's .roles() method prepends "ROLE_" automatically.
                // We MUST check for the full string "ROLE_ADMIN" and "ROLE_GATEMAN".
                if (role.equals("ROLE_ADMIN")) {
                    return "redirect:/admin/dashboard";
                } else if (role.equals("ROLE_GATEMAN")) {
                    return "redirect:/gateman/dashboard";
                }
            }
        }
        
        // If not logged in or role not found, go to login
        return "redirect:/login";
    }
}