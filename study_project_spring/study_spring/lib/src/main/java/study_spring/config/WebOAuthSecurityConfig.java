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
	
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	// 토큰방식을 사용하기위해 폼로그인 세션 비활성화 처리 
//    	http.csrf().disable()
//    		.httpBasic().disable() 
//    		.formLogin().disable() 
//    		.logout().disable();
//    	
//    	http.sessionManagement()
//    		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    	
//    	http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//
//        http.authorizeRequests()
//                .requestMatchers("/api/token").permitAll()
//                .requestMatchers("/api/**").authenticated()
//                .anyRequest().permitAll();
//
//        http.oauth2Login()
//                .loginPage("/login")
//                .authorizationEndpoint()
//                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
//                .and()
//                .successHandler(oAuth2SuccessHandler())
//                .userInfoEndpoint()
//                .userService(oAuth2UserCustomService);
//
//        http.logout()
//                .logoutSuccessUrl("/login");
//
//
//        http.exceptionHandling()
//                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
//                        new AntPathRequestMatcher("/api/**"));
//
//
//        return http.build();
//    	
//    	return null;
//    }
}
