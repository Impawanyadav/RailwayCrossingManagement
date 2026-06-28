package in.py.main.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import in.py.main.Dto.TrainDto;
import in.py.main.entity.Train;
import in.py.main.repository.TrainRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TrainServiceImp implements TrainService {
	private final ModelMapper modelMapper;
	private final TrainRepository trainRepository;
	@Override
	@Transactional
	public String addTrain(TrainDto dto) {
		// TODO Auto-generated method stub
		
		if (trainRepository.existsByTrainNumber(dto.getTrainNumber())) {
			throw new RuntimeException("Train Number" + dto.getTrainNumber() +"Already exist");
			
		}
		
		Train train=modelMapper.map(dto, Train.class);
		
		trainRepository.save(train);
		return "Success! Train " + train.getTrainNumber() + " (" + train.getTrainName() + ") has been added to the system.";
    
	}
	
	

}
