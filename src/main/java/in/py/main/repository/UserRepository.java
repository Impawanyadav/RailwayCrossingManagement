package in.py.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.py.main.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

}
