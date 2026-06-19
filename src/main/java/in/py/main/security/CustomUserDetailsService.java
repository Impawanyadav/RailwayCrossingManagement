package in.py.main.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.py.main.entity.User;
import in.py.main.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	
	
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		
		User user=userRepository.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("Email not found"));
		
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRole().replace("ROLE_", ""))
				.build();
		
		
	}

}
