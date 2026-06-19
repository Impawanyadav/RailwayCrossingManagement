package in.py.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.py.main.entity.DutyAssign;
@Repository
public interface DutyAssignRepository extends JpaRepository<DutyAssign, Long> {

}
