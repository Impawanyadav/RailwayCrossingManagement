package in.py.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.py.main.Dto.TrainDto;
import in.py.main.entity.Train;
import java.util.List;


public interface TrainRepository extends JpaRepository<Train, Long>{
	
	boolean existsByTrainNumber(int trainNumber);
	Optional<Train>findByTrainNumber(int trainNumber);

}
