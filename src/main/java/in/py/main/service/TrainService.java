package in.py.main.service;

import org.springframework.stereotype.Service;

import in.py.main.Dto.TrainDto;

@Service
public interface TrainService {
	
	public String addTrain(TrainDto dto);

}
