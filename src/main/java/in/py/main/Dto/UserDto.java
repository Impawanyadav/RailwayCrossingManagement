package in.py.main.Dto;


import lombok.Data;

@Data

public class UserDto {
	private Long id;
	private String role;
	private String name;
	private String email;
	private String currShift;
	
	public String getDisplayRole() {
        return role != null ? role.replace("ROLE_", "") : "";}

	public void setDisplayRole(String role) {
		// TODO Auto-generated method stub
		
	}

}
