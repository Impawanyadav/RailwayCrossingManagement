package in.py.main.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.py.main.entity.DailyCrossingLogsRealTime;

public interface DailyCrossingLogsRealTimeRepository extends JpaRepository<DailyCrossingLogsRealTime, Long> {

	List<DailyCrossingLogsRealTime> findByUserIdAndRailwayCrossingIdAndClosedToIsNull(Long userId, Long crossingId);
	
	List<DailyCrossingLogsRealTime>findByRailwayCrossingIdAndDateAndClosedToIsNotNull(Long crossingId, LocalDate date);
}
