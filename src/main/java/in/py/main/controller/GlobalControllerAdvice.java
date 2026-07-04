package in.py.main.controller;

import in.py.main.Dto.UserDto;
import in.py.main.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import lombok.AllArgsConstructor;

@ControllerAdvice
@AllArgsConstructor
public class GlobalControllerAdvice {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @ModelAttribute("userDto")
    public UserDto getLoggedInUserDto() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            String email = auth.getName();
            return userRepository.findByEmail(email)
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .orElse(null);
        }
        return null;
    }
}