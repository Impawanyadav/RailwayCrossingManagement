package in.py.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.py.main.entity.RailwayCrossing;

@Repository
public interface RailwayCrossingRepository extends JpaRepository<RailwayCrossing, Long>{

}
