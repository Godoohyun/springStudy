package study_spring.config.oauth;

import java.time.Duration;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import study_spring.config.jwt.TokenProvider;
import study_spring.repository.RefreshTokenRepository;
import study_spring.service.UserService;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler {
	private static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
	private static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14); // 14일 설정
	private static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1); // 1일
	private static final String REDIRECT_PATH = "/articles"; // 1일
	
	private final TokenProvider tokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
	private final UserService userService;
	
	
}
