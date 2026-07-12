package in.py.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.py.main.entity.CrossingLog;

@Repository
public interface CrossingLogRepository extends JpaRepository<CrossingLog, Long> {
	
   
    @Query("SELECT cl FROM CrossingLog cl " +
           "JOIN FETCH cl.train " +
           "LEFT JOIN FETCH cl.days " +
           "WHERE cl.railwayCrossing.id = :id")
    List<CrossingLog> findByRailwayCrossing_Id(@Param("id") Long id);
	
    
    @Query("SELECT cl FROM CrossingLog cl " +
           "JOIN FETCH cl.train " +
           "LEFT JOIN FETCH cl.days " +
           "WHERE cl.railwayCrossing.id = :id " +
           "ORDER BY cl.closedFrom ASC")
    List<CrossingLog> findByRailwayCrossing_IdOrderByClosedFromAsc(@Param("id") Long id);
	
    Optional<CrossingLog> findByTrain_TrainNumberAndRailwayCrossing_Id(int trainNumber, Long crossingId);
}