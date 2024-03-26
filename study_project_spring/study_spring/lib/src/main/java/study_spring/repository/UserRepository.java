package study_spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import study_spring.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
