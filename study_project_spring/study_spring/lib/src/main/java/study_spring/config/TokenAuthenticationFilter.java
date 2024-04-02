package study_spring.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import study_spring.config.jwt.TokenProvider;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	
	 private final TokenProvider tokenProvider;

	    private final static String HEADER_AUTHORIZATION = "Authorization";
	    private final static String TOKEN_PREFIX = "Bearer "; 

	    @Override
	    protected void doFilterInternal(
	            HttpServletRequest request,
	            HttpServletResponse response,
	            FilterChain filterChain)  throws ServletException, IOException {

	    	// 요청에서 헤더 가져와 
	        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
	        String token = getAccessToken(authorizationHeader);

	        // 검증갈겨
	        if (tokenProvider.validToken(token)) {
	            Authentication authentication = tokenProvider.getAuthentication(token);
	            // 홀더에 인증정보 저장시켜버려 
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }

	        filterChain.doFilter(request, response);
	    }

	    // 토큰추려내 Bearer 부분 추려내서 가져와 토큰만 남겨
	    private String getAccessToken(String authorizationHeader) {
	        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
	            return authorizationHeader.substring(TOKEN_PREFIX.length());
	        }

	        return null;
	    }

}
