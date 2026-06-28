package in.py.main.Dto;

import lombok.Data;

@Data
public class UserDto {
	private Long id;
	private String role;
	private String name;
	private String email;
	private String currshift;

}
