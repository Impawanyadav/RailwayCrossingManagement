package in.py.main.service;

import org.springframework.stereotype.Service;

import in.py.main.Dto.AutoAssignRequestDto;

@Service
public interface DutyAssignService {
	
	public String autoAssignGateman(AutoAssignRequestDto dto);

}
