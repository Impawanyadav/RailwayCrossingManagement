package in.py.main.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{
	 @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	            Authentication authentication) throws IOException, ServletException {
	        
	        String redirectUrl = null;

	        // Get user roles
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

	        for (GrantedAuthority grantedAuthority : authorities) {
	            String role = grantedAuthority.getAuthority();
	            
	            // Note: CustomUserDetailsService strips "ROLE_" prefix, so roles will be ADMIN or GATEMAN
	            if (role.equals("ROLE_ADMIN")) {
	                redirectUrl = "/admin/dashboard"; 
	                break;
	            } else if (role.equals("ROLE_GATEMAN")) {
	                redirectUrl = "/gateman/dashboard"; 
	                break;
	            }
	        }

	        if (redirectUrl == null) {
	            redirectUrl = "/login?error=invalid_role";
	        }

	        response.sendRedirect(redirectUrl);
	    }

}
