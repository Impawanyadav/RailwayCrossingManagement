package in.py.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.py.main.entity.CrossingLog;
import in.py.main.entity.RailwayCrossing;

@Repository
public interface CrossingLogRepository extends JpaRepository<CrossingLog, Long> {
	
	List<CrossingLog> findByRailwayCrossing_Id(Long id);
	List<CrossingLog> findByRailwayCrossing_IdOrderByClosedFromAsc(Long id);
	
	
	Optional<CrossingLog> findByTrain_TrainNumberAndRailwayCrossing_Id(int trainNumber, Long crossingId);
	

}
