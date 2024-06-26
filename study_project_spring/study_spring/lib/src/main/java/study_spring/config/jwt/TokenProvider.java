package study_spring.config.jwt;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import study_spring.domain.User;

@RequiredArgsConstructor
@Service
public class TokenProvider {
	
	private final JwtProperties jwtProperties;
	
	public String generateToken(User user, Duration expiredAt) {
		Date now = new Date();
		return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
	}

	private String makeToken(Date expriy, User user) {
		Date now = new Date();
		
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)  	// 헤더타입 세팅	{"typ": "JWT"}  참조 JWT IO
				.setIssuer(jwtProperties.getIssuer()) 			// Issure 설정값 세팅
				.setIssuedAt(now)								// 발급시간 세팅 (현재시간) 
				.setExpiration(expriy)							// 만료시간 세팅
				.setSubject(user.getEmail())					// 이 토큰이 누굴 위한건지 설정
				.claim("id", user.getId())						// 클레임 설정 (비공개, 공개, 등록된 클레임이 있음) { "id" : "test" } 비공개 클레임
				.signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 셋팅 알고리즘 헤더에 {"alg": "HS256"} 들어가는것과 같다.   
				.compact();										// 직렬화 토큰생성
	}
	
	/**
	 * JWT 토큰 유효성 검증 (복호화의 에러유무에 따라 검증)
	 */
	public boolean validToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(jwtProperties.getSecretKey()) // secretKey가져와서 세팅
				.parseClaimsJws(token);						 // 시크릿키로 token복호화 검증;
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 토큰사용 인증정보 가져오기 
	 */
	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token); 																			// 토큰에 담겨있는 클레임 가져오기 (공개, 비공개, 등록 등)
		Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));	// ROLE_USER 권한을 가진 관리자로 보면됨
		
		return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User  		// 스프링 시큐리티 라이브러리 유저 내가 생성한 도메인 유저와 무관
				(claims.getSubject(), "", authorities), token, authorities);
	}
	
	public Long getUserId(String token) {
		Claims claims = getClaims(token);
		return claims.get("id", Long.class);
	}
	
	private Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(jwtProperties.getSecretKey())
				.parseClaimsJws(token)				
				.getBody();					// 복호화 성공 후 claim 반납
	}
	
}
