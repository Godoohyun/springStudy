package study_spring.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;
import study_spring.config.jwt.TokenProvider;
import study_spring.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import study_spring.config.oauth.OAuth2SuccessHandler;
import study_spring.config.oauth.OAuth2UserCustomService;
import study_spring.repository.RefreshTokenRepository;
import study_spring.service.UserService;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {
	private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    
    @Bean
    public WebSecurityCustomizer configure() {
    	return (web) -> web.ignoring()
    			.requestMatchers(toH2Console())
    			.requestMatchers("/img/**", "/css/**", "/js/**"); // 필터검사 제외
    }
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	// 토큰방식을 사용하기위해 폼로그인 세션 비활성화 처리 
    	http.csrf().disable()
    		.httpBasic().disable() 
    		.formLogin().disable() 
    		.logout().disable();
    	
    	http.sessionManagement()
    		.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션없이 고우고우
    	
    	http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .requestMatchers("/api/token").permitAll()  // 인증없이 접근가능
                .requestMatchers("/api/**").authenticated() // 나머지 모든거에 대해 인증해
                .anyRequest().permitAll(); 					// /api가 아닌 요청에 대해선 인증없이 수락해

        http.oauth2Login()
                .loginPage("/login") 						// 로긴페이지로 가 /login
                .authorizationEndpoint()	
                // Authorization 요청과 관련된 상태 저장
                .authorizationRequestRepository(OAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .successHandler(oAuth2SuccessHandler())
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService);

        http.logout()
                .logoutSuccessUrl("/login"); // 로그아웃하면 여기로가 

        // 잘못되면 401 인증오류 내뱉어
        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**"));


        return http.build();
    }
    
    @Bean
    private OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
		
    	return new OAuth2AuthorizationRequestBasedOnCookieRepository();
	}

	@Bean
    private OAuth2SuccessHandler oAuth2SuccessHandler() {
    	
		return new OAuth2SuccessHandler(tokenProvider, refreshTokenRepository,
				oAuth2AuthorizationRequestBasedOnCookieRepository(),
				userService);
	}

	@Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }
}
