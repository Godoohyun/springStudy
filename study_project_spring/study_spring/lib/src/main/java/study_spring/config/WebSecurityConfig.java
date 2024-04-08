//package study_spring.config;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import lombok.RequiredArgsConstructor;
//import study_spring.service.UserDetailService;
//
//@RequiredArgsConstructor
//@Configuration
//public class WebSecurityConfig {
//	
//	private final UserDetailService userService;
//	
//	// 인증,인가 대상 접속가능 예를 들면 API endPoint로 처리
//	@Bean
//	public WebSecurityCustomizer configure() {
//		return (web) -> web.ignoring()
//				.requestMatchers(toH2Console()) // toH2Console  import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//				.requestMatchers("/static/**");
//	}
//
//	// configure 에서 예외처리 하지 않은 요청에 대해 필터처리
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		
//		return http
//				.authorizeHttpRequests() // 인증, 인가 설정
//				.requestMatchers("/login", "/signup", "/user").permitAll() // 해당 url로 요청이 오는건 인증 인가를 거치지 않고 허락
//				.anyRequest().authenticated() // 인증을 거쳐야함 인가 X
//				.and()
//				.formLogin() // 폼으로 로그인 설정 /login 페이지고 로긴성공하면 
//				.loginPage("/login")
//				.defaultSuccessUrl("/articles") // 기본페이지 /article
//				.and()
//				.logout() // 로그아웃 설정
//				.logoutSuccessUrl("/login")
//				.invalidateHttpSession(true) // 세션초기화
//				.and()
//				.csrf().disable() // csrf 비활성
//				.build();
//	}
//	
//	// 인증관리자가 행하는 매써드 설정
//	@Bean
//	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder
//			, UserDetailService userDetailService) throws Exception {
//		return http.getSharedObject(AuthenticationManagerBuilder.class)
//				.userDetailsService(userService)
//				.passwordEncoder(bCryptPasswordEncoder) // 암호화 
//				.and() 
//				.build();
//	}
//	
//	// 패스워드 인코더로 BCryptPasswordEncoder 빈 등록
//	@Bean
//	public BCryptPasswordEncoder bCryptPasswordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
//}
