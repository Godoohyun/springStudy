package study_spring.config.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.jsonwebtoken.Jwts;
import study_spring.domain.User;
import study_spring.repository.UserRepository;


@SpringBootTest
public class TokenProviderTest {
	@Autowired
	private TokenProvider tokenProvider;
	@Autowired // 이게 빈주입 받는것도 아닌거 같은데 필요한가?
	private UserRepository userRepository; // Repository는 자동 빈 등록인거 같은데 @Autowired로 빈주입을 못받고 왜 임포트 해야하는가?
	@Autowired
	private JwtProperties jwtProperties; 
	
	@DisplayName("generateToken(): 유저정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
	@Test
	void generateToken() {
		// given
		User testUser = userRepository.save(User.builder()
				.email("user@gmail.com")
				.password("test")
				.build()
				);
		
		// when
		String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));
		
		// then
		Long userId = Jwts.parser()
				.setSigningKey(jwtProperties.getSecretKey())
				.parseClaimsJws(token)
				.getBody()
				.get("id", Long.class);
		
		assertThat(userId).isEqualTo(testUser.getId());
	}
}
