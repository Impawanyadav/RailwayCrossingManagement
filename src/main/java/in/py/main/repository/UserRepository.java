package in.py.main.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.py.main.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Optional<User> findById(Long id);
	
	@Query("SELECT u FROM User u " +
	           "WHERE u.role = 'GATEMAN' " +
	           "AND u.status = 'ACTIVE' " +
	           "AND u.currShift = :shift " +
	           "AND u.id NOT IN (SELECT busy.user.id FROM DutyAssign busy WHERE busy.dutyDate = :dutyDate) " +
	           "ORDER BY (SELECT COUNT(d) FROM DutyAssign d WHERE d.user.id = u.id AND d.railwayCrossing.id = :crossingId) DESC")
	    List<User> findBestGatemanForDuty(
	            @Param("crossingId") Long crossingId, 
	            @Param("shift") String shift, 
	            @Param("dutyDate") LocalDate dutyDate, 
	            Pageable pageable);

}
