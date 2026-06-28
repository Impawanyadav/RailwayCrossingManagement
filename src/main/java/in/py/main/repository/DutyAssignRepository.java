package in.py.main.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.py.main.entity.DutyAssign;
@Repository
public interface DutyAssignRepository extends JpaRepository<DutyAssign, Long> {
	

	boolean existsByRailwayCrossingIdAndDutyDateAndShift(Long crossingId, LocalDate dutyDate, String shift);
	
	
	@Query("SELECT d FROM DutyAssign d WHERE d.railwayCrossing.id = :crossingId AND d.status = 'ONGOING'")
    Optional<DutyAssign> findActiveDutyByCrossingId(@Param("crossingId") Long crossingId);
	
	@Query("SELECT d FROM DutyAssign d WHERE d.user.id = :gatemanId AND d.status = 'UPCOMING' AND d.dutyDate >= CURRENT_DATE ORDER BY d.dutyDate ASC")
    List<DutyAssign> findUpcomingDutiesByGatemanId(@Param("gatemanId") Long gatemanId); 
	
    
	@Query("SELECT d FROM DutyAssign d WHERE d.railwayCrossing.id = :crossingId AND d.user.id = :gatemanId AND d.status = 'UPCOMING' AND d.dutyDate = CURRENT_DATE")
	Optional<DutyAssign> findTodayUpcomingDuty(@Param("crossingId") Long crossingId, @Param("gatemanId") Long gatemanId);
}
