package in.py.main.security;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final CustomSuccessHandler customSuccessHandler;

	public SecurityConfig(CustomSuccessHandler customSuccessHandler) {
		this.customSuccessHandler = customSuccessHandler;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
		.csrf(csrf-> csrf.disable())
		.authorizeHttpRequests(auth-> auth
			
				.requestMatchers("/auth/**", "/css/**", "/js/**", "/login").permitAll()
				.requestMatchers("/api/admin/**").hasRole("ADMIN")
				
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/gateman/**").hasRole("GATEMAN")
				
				.anyRequest().authenticated()
		)
		.formLogin(form -> form
				.loginPage("/login") 
				.successHandler(customSuccessHandler) 
				.permitAll()
		)
		.logout(logout -> logout
				.logoutUrl("/logOut")
				.logoutSuccessUrl("/login?logout")
				.permitAll()
		);
		
		return http.build();
	}
}