package in.py.main.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.py.main.entity.DailyCrossingLogsRealTime;

@Repository
public interface DailyCrossingLogsRealTimeRepository extends JpaRepository<DailyCrossingLogsRealTime, Long> {

   
    @Query("SELECT d FROM DailyCrossingLogsRealTime d " +
           "LEFT JOIN FETCH d.railwayCrossing " +
           "WHERE d.user.id = :userId " +
           "AND d.railwayCrossing.id = :crossingId " +
           "AND d.closedTo IS NULL")
    List<DailyCrossingLogsRealTime> findByUserIdAndRailwayCrossingIdAndClosedToIsNull(
            @Param("userId") Long userId, 
            @Param("crossingId") Long crossingId);
	
   
    @Query("SELECT d FROM DailyCrossingLogsRealTime d " +
           "JOIN FETCH d.user " +
           "JOIN FETCH d.railwayCrossing " +
           "WHERE d.railwayCrossing.id = :crossingId " +
           "AND d.date = :date " +
           "AND d.closedTo IS NOT NULL")
    List<DailyCrossingLogsRealTime> findByRailwayCrossingIdAndDateAndClosedToIsNotNull(
            @Param("crossingId") Long crossingId, 
            @Param("date") LocalDate date);
}