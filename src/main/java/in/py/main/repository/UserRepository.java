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
		       "WHERE u.role = 'ROLE_GATEMAN' " +
		       "AND u.status = 'ACTIVE' " +
		       "AND u.currShift = :shift " +
		       "AND u.id NOT IN (SELECT busy.user.id FROM DutyAssign busy WHERE busy.dutyDate = :dutyDate) " +
		       "ORDER BY " +
		       "(SELECT COUNT(d1) FROM DutyAssign d1 WHERE d1.user.id = u.id AND d1.railwayCrossing.id = :crossingId) DESC, " +
		       "(SELECT COUNT(d2) FROM DutyAssign d2 WHERE d2.user.id = u.id) ASC")
		List<User> findBestGatemanForDuty(
		        @Param("crossingId") Long crossingId, 
		        @Param("shift") String shift, 
		        @Param("dutyDate") LocalDate dutyDate, 
		        Pageable pageable);
}
