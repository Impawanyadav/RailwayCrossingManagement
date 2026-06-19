package in.py.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.py.main.entity.Train;

public interface TrainRepository extends JpaRepository<Train, Long>{

}
