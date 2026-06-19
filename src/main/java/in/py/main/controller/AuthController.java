package in.py.main.controller;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.py.main.Dto.LoginDto;
import in.py.main.Dto.SignUpDto;
import in.py.main.entity.User;
import in.py.main.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final ModelMapper modelMapper;
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
		if(userRepository.findByEmail(signUpDto.getEmail()).isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Email is already in use!");
		}
		
		User newUser=modelMapper.map(signUpDto, User.class);
		newUser.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		newUser.setStatus("ACTIVE");
		userRepository.save(newUser);
		return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto ){
		try {
			Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()))
			
					User user=userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
			Map<String, Object> response=new HashMap<>();
			response.put("id", user.getId());
			response.put("email", user.getEmail());
			response.put("name", user.getName());
			response.put("role", user.getRole());
			return ResponseEntity.ok(response);
			
					
					
			
					
		}
		catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
		}
	}

}
