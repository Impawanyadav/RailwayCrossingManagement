package in.py.main.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.py.main.entity.RailwayCrossing;

@Repository
public interface RailwayCrossingRepository extends JpaRepository<RailwayCrossing, Long>{
	
	Optional<RailwayCrossing> findById(Long id);
	
	@Query("SELECT r FROM RailwayCrossing r WHERE :id IS NULL OR CAST(r.id AS string) LIKE CONCAT(:id, '%')")
    List<RailwayCrossing> searchByIdOrLoadAll(@Param("id") Long id);
	@Query("SELECT c FROM RailwayCrossing c " +
	           "WHERE c.status = 'ACTIVE' " +
	           "AND c.id NOT IN (" +
	           "    SELECT d.railwayCrossing.id FROM DutyAssign d " +
	           "    WHERE d.dutyDate = :dutyDate AND d.shift = :shift" +
	           ")")
	    List<RailwayCrossing> findAvailableCrossings(
	            @Param("dutyDate") LocalDate dutyDate, 
	            @Param("shift") String shift);
	
	
}
