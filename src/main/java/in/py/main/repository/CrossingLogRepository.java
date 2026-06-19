package in.py.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.py.main.entity.CrossingLog;

@Repository
public interface CrossingLogRepository extends JpaRepository<CrossingLog, Long> {

}
