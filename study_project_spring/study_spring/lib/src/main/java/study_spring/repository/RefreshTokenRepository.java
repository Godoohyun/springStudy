package study_spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import study_spring.domain.RefreshToken;
import study_spring.domain.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByUserId(Long userId);
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
